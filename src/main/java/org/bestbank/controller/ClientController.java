package org.bestbank.controller;

import lombok.RequiredArgsConstructor;
import org.bestbank.controller.dto.ClientRequest;
import org.bestbank.controller.dto.ClientResponse;
import org.bestbank.repository.entity.Client;
import org.bestbank.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @GetMapping(path = "/api/user")
    public ResponseEntity<ClientResponse> findByEmail(@RequestParam String email){
        ClientResponse clientResponse = service.findResponseByEmail(email);
        return new ResponseEntity<>(clientResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/api/user")
    public ResponseEntity<Client> createClient(@RequestBody ClientRequest clientRequest){
        service.save(clientRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
