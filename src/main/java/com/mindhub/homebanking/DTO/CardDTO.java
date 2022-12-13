package com.mindhub.homebanking.DTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;
public class CardDTO {
    private long id;
    private String cardHolder;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private CardType cardType;
    private CardColor cardColor;

    public CardDTO() {
    }

    public CardDTO(Card card){
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }
}
