package org.bestbank.controller;

import lombok.RequiredArgsConstructor;
import org.bestbank.controller.dto.AccountRequest;
import org.bestbank.controller.dto.TransactionRequest;
import org.bestbank.repository.entity.Client;
import org.bestbank.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(path = "/api/transaction")
    public ResponseEntity<Client> createTransaction(@RequestBody TransactionRequest transactionRequest){
        transactionService.createTransaction(transactionRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
