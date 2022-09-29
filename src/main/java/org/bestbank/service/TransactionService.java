package org.bestbank.service;

import lombok.RequiredArgsConstructor;
import org.bestbank.controller.dto.AccountResponse;
import org.bestbank.controller.dto.TransactionRequest;
import org.bestbank.repository.AccountRepository;
import org.bestbank.repository.TransactionRepository;
import org.bestbank.repository.entity.Transaction;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountService accountService;
    private final TimeUtils timeUtils;

    public void createTransaction(TransactionRequest transactionRequest){
        if(transactionRequest.getAmount() <= 0){
            throw new IllegalArgumentException("Amount must be positive!");
        }
        if(transactionRequest.getCurrency() == null){
            throw new IllegalArgumentException("Currency cannot be null!");
        }
        if(transactionRequest.getCurrency().isEmpty()){
            throw new IllegalArgumentException("Currency cannot be empty!");
        }

        AccountResponse fromAccount = accountService.findById(transactionRequest.getFrom_account_id());
        AccountResponse toAccount = accountService.findById(transactionRequest.getTo_account_id());

        if(fromAccount == null){
            throw new IllegalArgumentException("FromAccount doesn't exist!");
        }
        if(toAccount == null){
            throw new IllegalArgumentException("ToAccount doesn't exist!");
        }

        accountService.transfer(transactionRequest.getFrom_account_id(), transactionRequest.getTo_account_id(), transactionRequest.getAmount());

        repository.save(Transaction.builder()
                .amount(transactionRequest.getAmount())
                .currency(transactionRequest.getCurrency())
                .transaction_date(timeUtils.getTime())
                .from_account_id(transactionRequest.getFrom_account_id())
                .to_account_id(transactionRequest.getTo_account_id())
                .build());
    }

}

