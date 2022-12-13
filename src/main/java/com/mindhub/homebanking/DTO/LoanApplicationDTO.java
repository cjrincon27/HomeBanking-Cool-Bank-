package com.mindhub.homebanking.DTO;
//PARA CREAR UN OBJETO DE SOLUCITUD DE CREDITO
public class LoanApplicationDTO {
    private Long id;
    private Double amount;
    private Integer payments;
    private String numberDestinAccount;

    public Long getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public String getNumberDestinAccount() {
        return numberDestinAccount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setNumberDestinAccount(String numberDestinAccount) {
        this.numberDestinAccount = numberDestinAccount;
    }
    public Integer getPayments() {
        return payments;
    }
    public void setPayments(Integer payments) {
        this.payments = payments;
    }

}
