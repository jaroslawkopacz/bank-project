package org.bestbank.service;

import lombok.RequiredArgsConstructor;
import org.bestbank.controller.dto.TransactionRequest;
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

