package com.haulmont.bankingApplication.services;

import com.haulmont.bankingApplication.models.Bank;
import com.haulmont.bankingApplication.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    public BankService(){}

    public List<Bank> findAll(){ return bankRepository.findAll(); }

    public void save(Bank bank){
        bankRepository.save(bank);
    }

    public void delete(Bank bank){
        bankRepository.delete(bank);
    }
}
