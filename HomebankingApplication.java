package com.mindhub.homebanking;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}
    @Bean
	public CommandLineRunner initData(ClientRepository clientRepository , AccountRepository accountRepository , TransactionRepository transactionRepository){

		return args -> {
			Client client1= new Client("Melba","Morel","MelbaMorel@gmail.com");

			clientRepository.save(client1);

			Account account1= new Account("VIN001", LocalDateTime.now(), 10000.0 , client1 );
			Account account2= new Account("VIN002", LocalDateTime.now().plusDays(1),20000.0, client1);
			Account account3= new Account("VIN003", LocalDateTime.now().plusDays(1),100000.0, client1);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			Transaction transaction1 = new Transaction(account1, TransactionType.DEBIT, -1000, "Services", LocalDateTime.now());
			Transaction transaction2 = new Transaction(account2, TransactionType.DEBIT, -2000, "Salary", LocalDateTime.now());
			Transaction transaction3 = new Transaction(account3, TransactionType.CREDIT, 5000, "Salary", LocalDateTime.now());
			Transaction transaction4 = new Transaction(account1, TransactionType.CREDIT, 1000, "Services", LocalDateTime.now());
			Transaction transaction5 = new Transaction(account2, TransactionType.CREDIT, 2000, "Salary", LocalDateTime.now());
			Transaction transaction6 = new Transaction(account3, TransactionType.CREDIT, -5000, "Salary", LocalDateTime.now());

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
		};
	}

}
