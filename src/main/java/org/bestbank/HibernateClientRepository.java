package org.bestbank;

import org.bestbank.Client;
import org.bestbank.ClientRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier
@Repository
public class HibernateClientRepository implements ClientRepository {
    @Override
    public void save(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        client.getAccounts().forEach(account -> session.save(account));
        session.save(client);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Client findByEmail(String email) {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        final Query<Client> query = session.createQuery("from Client where mail=:mail", Client.class);
        query.setParameter("mail", email);
        Client client = query.uniqueResult();
        session.close();
        return client;
    }
}
