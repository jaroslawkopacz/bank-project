package org.bestbank.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "TRANSACTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long id;
    @Column(name = "AMOUNT")
    private double amount;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "TRANSACTION_DATE")
    private OffsetDateTime transaction_date;
    @Column(name = "FROM_ACCOUNT_ID")
    private long from_account_id;
    @Column(name = "TO_ACCOUNT_ID")
    private long to_account_id;
}
