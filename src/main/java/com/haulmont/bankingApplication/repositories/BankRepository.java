package com.haulmont.bankingApplication.repositories;

import com.haulmont.bankingApplication.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
}
