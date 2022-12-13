package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.Configurations.WebAuthentication;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
@RestController
public class ClientController {
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private AccountServices accountServices;

    @Autowired
    private PasswordEncoder passwordEncoder; ////////////

    @RequestMapping("/api/clients")//clientes
    public List<ClientDTO> getClients() {
        return clientServices.getClientDTO();
    }

    @RequestMapping("/api/clients/{id}")//cliente
    public ClientDTO getClient(@PathVariable Long id) {
        return clientServices.getClientbyId(id);
    }


    @PostMapping("/api/clients")//registrar
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing in Name", HttpStatus.FORBIDDEN);
        }

        if ( lastName.isEmpty()) {
            return new ResponseEntity<>("Missing in LastName", HttpStatus.FORBIDDEN);
        }

        if (password.isEmpty()) {
            return new ResponseEntity<>("Missing inEmail", HttpStatus.FORBIDDEN);
        }


        if (clientServices.findByEmail(email) != null) {
            return new ResponseEntity<>("Email  in use", HttpStatus.FORBIDDEN);

        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientServices.saveClient(newClient);
        String randomNumber = "VIN-" + getRandomNumber(1, 100000000);
        Account account= new Account(randomNumber, LocalDateTime.now(), 0, newClient);
        accountServices.saveAccount(account);

        return new ResponseEntity<>( HttpStatus.CREATED);

    }



    @GetMapping("/api/clients/current")
    public ClientDTO getall(Authentication authentication) {
        return new ClientDTO(clientServices.findByEmail(authentication.getName()));
    }

    public int getRandomNumber (int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
