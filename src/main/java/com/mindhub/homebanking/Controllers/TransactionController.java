package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.Services.TransactionServices;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
@RestController
public class TransactionController {
    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private AccountServices accountServices;

    @RequestMapping("/api/transactions" )
    public List<TransactionDTO> getTransactions() {
        return transactionServices.getTransactionDTO();
    }

    @RequestMapping("/api/transactions/{id}")
    public TransactionDTO getTransactions(@PathVariable Long id) {
      return transactionServices.getTransactionbyId(id);
    }


    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount, @RequestParam String description,
                                                 @RequestParam String accountOrigin, @RequestParam String accountDestiny,
                                                 Authentication authentication) {

        Client clientAuthentication = clientServices.findByEmail(authentication.getName());
        Account newOriginAccount = accountServices.findByNumber(accountOrigin);
        Account newDestinyAccount = accountServices.findByNumber(accountDestiny);
        Set <Account> accountExist= clientAuthentication.getAccounts().stream().filter(account -> account.getNumber().equals(accountOrigin)).collect(Collectors.toSet());


        if (clientAuthentication != null) {

            if (amount <= 0) {
                return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
            }

            if (description.isEmpty()) {
                return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
            }

            if (accountOrigin.isEmpty()) {
                return new ResponseEntity<>("missing accountOrigin", HttpStatus.FORBIDDEN);
            }

            if (accountDestiny.isEmpty()) {
                return new ResponseEntity<>("missing accountDestiny", HttpStatus.FORBIDDEN);
            }


            if (accountExist.isEmpty()) {
                return new ResponseEntity<>("missing account", HttpStatus.FORBIDDEN);
            }


            if (accountOrigin.equals(accountDestiny)) {
                return new ResponseEntity<>("Account origin and destiny are the same", HttpStatus.FORBIDDEN);
            }


            if (newOriginAccount == null) {
                return new ResponseEntity<>("Origin account doesn't exist", HttpStatus.FORBIDDEN);
            }
            if (newDestinyAccount == null) {
                return new ResponseEntity<>("Destiny account doesn't exist", HttpStatus.FORBIDDEN);
            }


            if (accountServices.findByNumber(accountOrigin).getBalance() <= amount) {
                return new ResponseEntity<>("Not enough money", HttpStatus.FORBIDDEN);
            }

            Transaction debitTransaction = new Transaction(newOriginAccount, TransactionType.CREDIT, amount, description + " go to " + newDestinyAccount.getNumber(), LocalDateTime.now());

            Transaction creditTransaction = new Transaction(newDestinyAccount, TransactionType.DEBIT, amount, description + " from " + newOriginAccount.getNumber(), LocalDateTime.now());

            transactionServices.saveTransaction(debitTransaction);
            transactionServices.saveTransaction(creditTransaction);


            newOriginAccount.setBalance(newOriginAccount.getBalance() - amount);
            newDestinyAccount.setBalance(newDestinyAccount.getBalance() + amount);

            accountServices.saveAccount(newOriginAccount);
            accountServices.saveAccount(newDestinyAccount);


            return new ResponseEntity<>(HttpStatus.CREATED);

        }


        return new ResponseEntity<>(HttpStatus.FORBIDDEN);


    }
}
