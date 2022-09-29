package org.bestbank.service;

import lombok.RequiredArgsConstructor;
import org.bestbank.controller.dto.AccountRequest;
import org.bestbank.controller.dto.AccountResponse;
import org.bestbank.repository.AccountRepository;
import org.bestbank.repository.ClientRepository;
import org.bestbank.repository.entity.Account;
import org.bestbank.repository.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository repository;
    private final ClientRepository clientRepository;

    public AccountResponse findById(long id) {
        Account account = repository.getOne(id);

        if(account == null){
            throw new NoSuchElementException("Account doesn't exist!");
        }

        AccountResponse accountResponse = AccountResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .user_id(account.getUser_id())
                .build();

        return accountResponse;
    }

    public void save(AccountRequest accountRequest) {
        if(accountRequest == null){
            throw new IllegalArgumentException("Account cannot be null!");
        }
        if(accountRequest.getCurrency() == null){
            throw new IllegalArgumentException("Currency cannot be null!");
        }
        if(accountRequest.getCurrency().isEmpty()){
            throw new IllegalArgumentException("Currency cannot be empty!");
        }
        if(accountRequest.getBalance() < 0){
            throw new IllegalArgumentException("Balance cannot be negative!");
        }

        Client client = clientRepository.getOne(accountRequest.getUser_id());

        if(client == null){
            throw new NoSuchElementException("User doesn't exist!");
        }

        Account account = Account.builder()
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .user_id(accountRequest.getUser_id())
                .build();

        repository.save(account);
    }

    public void transfer(long fromId, long toId, double amount){
        if(fromId == toId){
            throw new IllegalArgumentException("You cannot transfer money to yourself!");
        }

        Account fromAccount = repository.getOne(fromId);
        Account toAccount = repository.getOne(toId);

        if(fromAccount == null){
            throw new IllegalArgumentException("FromAccount doesn't exist!");
        }
        if(toAccount == null){
            throw new IllegalArgumentException("toAccount doesn't exist!");
        }

        if(amount > 0 && fromAccount.getBalance() - amount >= 0){
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
        } else {
            throw new IllegalArgumentException("Not enough funds!");
        }
        repository.save(fromAccount);
        repository.save(toAccount);
    }




}
