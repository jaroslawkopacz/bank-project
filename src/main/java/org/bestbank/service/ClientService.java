package org.bestbank.service;

import org.bestbank.controller.dto.ClientRequest;
import org.bestbank.controller.dto.ClientResponse;
import org.bestbank.repository.entity.Client;
import org.bestbank.repository.ClientSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ClientService {
    private ClientSpringDataJpaRepository repository;

    @Autowired
    public ClientService(ClientSpringDataJpaRepository repository) {
        this.repository = repository;
    }

    public void save(ClientRequest clientRequest){
        if(clientRequest == null){
            throw new IllegalArgumentException("Client cannot be null!");
        }
        if(clientRequest.getName() == null){
            throw new IllegalArgumentException("Name cannot be null!");
        }
        if(clientRequest.getEmail() == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }

        Client client = Client.builder()
                .name(clientRequest.getName())
                .email(clientRequest.getEmail())
                .build();

        repository.save(client);
    }

    public Client findByEmail (String email){
        if(email == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if(email.isEmpty()){
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        String toLowerCaseEmail = email.toLowerCase();
        Client client = repository.findByEmail(toLowerCaseEmail);

        if(client == null) {
            throw new NoSuchElementException("Client doesn't exist!");
        }

        return client;
    }

    public ClientResponse findResponseByEmail (String email){
        if(email == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if(email.isEmpty()){
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        String toLowerCaseEmail = email.toLowerCase();
        Client client = repository.findByEmail(toLowerCaseEmail);

        if(client == null) {
            throw new NoSuchElementException("Client doesn't exist!");
        }

        ClientResponse clientResponse = ClientResponse
                .builder()
                .name(client.getName())
                .email(client.getEmail())
                .accounts(client.getAccounts())
                .build();

        return clientResponse;
    }

    public void withdraw(String email, double amount) {
        if(email == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if(email.isEmpty()){
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be positive!");
        }
        if(amount == 0){
            throw new IllegalArgumentException("You cannot withdraw 0!");
        }


        String toLowerCaseEmail = email.toLowerCase();
        Client client = repository.findByEmail(toLowerCaseEmail);



        if(amount > client.getBalance()){
            throw new IllegalArgumentException("No sufficient founds!");
        }

        double balance = client.getBalance() - amount;
        client.setBalance(balance);
        repository.save(client);
    }
}
