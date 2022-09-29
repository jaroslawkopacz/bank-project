package org.bestbank.repository;

import org.bestbank.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

//    @Query("SELECT c FROM Client c where c.email= :email")
    Client findByEmail(@Param("email") String email);
}
