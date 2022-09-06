package org.bestbank.service;

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

    public void save(Client client){
        if(client == null){
            throw new IllegalArgumentException("Client cannot be null!");
        }
        if(client.getName() == null){
            throw new IllegalArgumentException("Name cannot be null!");
        }
        if(client.getEmail() == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if(client.getBalance() < 0){
            throw new IllegalArgumentException("Balance cannot be negative!!");
        }

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

    public void transfer(String fromEmail, String toEmail, double amount){
        if(fromEmail == null || toEmail == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if(fromEmail.isEmpty() || toEmail.isEmpty()){
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if(fromEmail.equals(toEmail)){
            throw new IllegalArgumentException("You cannot transfer money to yourself!");
        }

        String toLowerCaseFromEmail = fromEmail.toLowerCase();
        String toLowerCaseToEmail = toEmail.toLowerCase();

        Client fromClient = findByEmail(toLowerCaseFromEmail);
        Client toClient = findByEmail(toLowerCaseToEmail);

        if(amount > 0 && fromClient.getBalance() - amount >= 0){
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        } else {
            throw new IllegalArgumentException("Not enough funds!");
        }
        repository.save(fromClient);
        repository.save(toClient);
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
