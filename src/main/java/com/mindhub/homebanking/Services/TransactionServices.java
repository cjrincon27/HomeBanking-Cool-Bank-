package com.mindhub.homebanking.Services;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;
public interface TransactionServices {
    public List<TransactionDTO> getTransactionDTO();
    public TransactionDTO getTransactionbyId(long id);
    public void saveTransaction(Transaction transaction);
}
