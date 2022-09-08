package org.bestbank.controller;

import lombok.RequiredArgsConstructor;
import org.bestbank.controller.dto.AccountRequest;
import org.bestbank.controller.dto.AccountResponse;
import org.bestbank.controller.dto.ClientRequest;
import org.bestbank.controller.dto.ClientResponse;
import org.bestbank.repository.entity.Client;
import org.bestbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;


    @GetMapping(path = "/api/account")
    public ResponseEntity<AccountResponse> findById(@RequestParam long id){
        AccountResponse accountResponse = service.findById(id);
        return new ResponseEntity<>(accountResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/api/account")
    public ResponseEntity<Client> createClient(@RequestBody AccountRequest accountRequest){
        service.save(accountRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
