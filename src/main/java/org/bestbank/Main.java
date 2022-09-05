package org.bestbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private ClientService clientService;

    @Autowired
    public Main(@Qualifier("HibernateClientRepository") ClientService clientService) {
        this.clientService = clientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("1 - Add user");
            System.out.println("2 - Find user");
            System.out.println("3 - Exit");

            String next = scanner.next();

            if(next.equals("1")){
                addUser();
            }

            if(next.equals("2")){
                findUser();
            }

            if(next.equals("3")){
                break;
            }


        }
    }

    private void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scanner.next();
        System.out.println("Enter email: ");
        String email = scanner.next();
        System.out.println("Enter balance: ");
        double balance = scanner.nextDouble();
        Account account = new Account(balance, "PLN");
        List<Account> accounts = List.of(account);
        clientService.save(new Client(name, email, accounts));
    }

    private void findUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = scanner.next();
        System.out.println(clientService.findByEmail(email));
    }
}
