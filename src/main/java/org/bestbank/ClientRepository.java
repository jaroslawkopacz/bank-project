package org.bestbank;

import org.bestbank.Client;

public interface ClientRepository {

    void save(Client client);

    Client findByEmail(String email);

}
