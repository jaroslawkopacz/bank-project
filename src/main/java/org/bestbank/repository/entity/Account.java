package org.bestbank.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;
    @Column(name = "BALANCE")
    private double balance;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "USER_ID")
    private Long user_id;

    public Account(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }
}
