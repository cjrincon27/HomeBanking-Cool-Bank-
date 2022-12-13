package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.Services.CardServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class CardController {
    @Autowired
    private CardServices cardServices;
    @Autowired
    private ClientServices clientServices;


    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> createNewCard(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        Client clientCard = clientServices.findByEmail(authentication.getName());

        int cardCvv = getRandomNumber(100, 999);

        String cardNumber = getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999);

        if (cardType == null ) {
            return new ResponseEntity<>("Card type  not found", HttpStatus.FORBIDDEN);

        }
        if ( cardColor == null) {
            return new ResponseEntity<>("Card color not found", HttpStatus.FORBIDDEN);
        }

        if (clientCard.getCards().stream().filter(card -> card.getCardType().equals(cardType)).count() >= 3) {
            return new ResponseEntity<>("You exceeded the number of cards", HttpStatus.FORBIDDEN);
        }


        if (clientCard.getCards().stream().filter(card -> card.getCardColor().equals(cardColor)).count() >= 3) {
            return new ResponseEntity<>("You exceeded the color  of cards", HttpStatus.FORBIDDEN);
        }


        cardServices.saveCard(new Card(clientCard, clientCard.getFirstName() + " " + clientCard.getLastName() , cardNumber, cardCvv, LocalDate.now(), LocalDate.now().plusYears(5), cardType, cardColor));
        return new ResponseEntity<>("Card created", HttpStatus.CREATED);


    }

    public int getRandomNumber (int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
