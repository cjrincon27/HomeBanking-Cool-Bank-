package com.mindhub.homebanking.Services.Implements;
import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountServiceImplement implements AccountServices {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public List<AccountDTO> getAccountDTO() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }
    @Override
    public AccountDTO  getAccountbyId( long id) {
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }
    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
