package com.haulmont.bankingApplication.repositories;

import com.haulmont.bankingApplication.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
