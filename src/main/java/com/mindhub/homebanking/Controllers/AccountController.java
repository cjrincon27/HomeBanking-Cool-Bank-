package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.DTO.AccountDTO;

import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;



@RestController
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ClientServices clientServices;


    @RequestMapping("/api/account")//cuentas
    public List<AccountDTO> getAccounts() {
        return accountServices.getAccountDTO();
    }

    @RequestMapping("/api/account/{id}")//movimientos cuenta
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountServices.getAccountbyId(id);

    }


    @PostMapping("/api/clients/current/account")//crear
    public ResponseEntity<Object> createNewCurrentAccount(Authentication authentication) {

        String randomNumber = "VIN-" + getRandomNumber( 10000000,99999999);

        Client newCurrentClient = clientServices.findByEmail(authentication.getName());

        if (newCurrentClient.getAccounts().size() >= 3) {
            return new ResponseEntity<>("you can't create more accounts", HttpStatus.FORBIDDEN);
        } else {

            accountServices.saveAccount(new Account(randomNumber, LocalDateTime.now(), 0.0, newCurrentClient));
            return new ResponseEntity<>("Account created", HttpStatus.CREATED);

        }
    }

    public int getRandomNumber (int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
