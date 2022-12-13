package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.Services.ClientLoanServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.Services.LoanServices;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
public class LoanController {
    @Autowired
    LoanServices loanServices;
    @Autowired
    ClientServices clientServices;
    @Autowired
    AccountServices accountServices;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientLoanServices clientLoanServices;

    @RequestMapping("/api/loans")//préstamos disponibles
    public List<LoanDTO> getLoanApplicationDTO() {
        return loanServices.getLoanDTO();
    }


    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> newLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client clientCurrent = clientServices.findByEmail(authentication.getName());
        Loan loanExist = loanServices.getLoanbyId(loanApplicationDTO.getId());
        Account accountDestiny = accountServices.findByNumber(loanApplicationDTO.getNumberDestinAccount());

        if (clientCurrent != null) {

            if (loanApplicationDTO == null) {
                return new ResponseEntity<>("not data ", HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getAmount() <= 0) {
                return new ResponseEntity<>(" amount  empty", HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getNumberDestinAccount().isEmpty()) {
                return new ResponseEntity<>("destination account  empty", HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getPayments() == null) {
                return new ResponseEntity<>(" payments empty" , HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getId() == null) {
                return new ResponseEntity<>(" loan empty", HttpStatus.FORBIDDEN);
            }
            if (accountDestiny == null) {
                return new ResponseEntity<>(  "error account", HttpStatus.FORBIDDEN);
            }
            if (!clientCurrent.getAccounts().contains(accountDestiny)) {
                return new ResponseEntity<>("accounts not authenticated", HttpStatus.FORBIDDEN);
            }
            if (loanExist == null) {
                return new ResponseEntity<>("the loan could not be verified", HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getAmount() > loanExist.getMaxAmount()) {
                return new ResponseEntity<>("exceeds the amount available", HttpStatus.FORBIDDEN);
            }
            if (!loanExist.getPayments().contains(loanApplicationDTO.getPayments())) {
                return new ResponseEntity<>("the payments are not among those available ", HttpStatus.FORBIDDEN);
            }
            if (clientCurrent.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan().getName().equals(loanExist.getName())).toArray().length == 1) {
                return new ResponseEntity<>("you already have a loan ", HttpStatus.FORBIDDEN);
            }//COMPRUEBO QUE NO TENGA EL LOAN

            if (loanApplicationDTO.getId() == 6) {
                ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.2, loanApplicationDTO.getPayments(), clientCurrent, loanExist);
                clientLoanServices.saveClientLoan(clientLoan);
            }

            if (loanApplicationDTO.getId()==7){
                ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.10, loanApplicationDTO.getPayments(), clientCurrent , loanExist);
                clientLoanServices.saveClientLoan(clientLoan);
            }
            if(loanApplicationDTO.getId()==8){
                ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.5, loanApplicationDTO.getPayments(), clientCurrent , loanExist);
                clientLoanServices.saveClientLoan(clientLoan);
            }

            Transaction transactionCreated = new Transaction(accountDestiny, TransactionType.CREDIT, loanApplicationDTO.getAmount(), " from " + accountDestiny.getNumber(), LocalDateTime.now());
            transactionRepository.save(transactionCreated);

            accountDestiny.setBalance(accountDestiny.getBalance() + loanApplicationDTO.getAmount());
            accountServices.saveAccount(accountDestiny);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
