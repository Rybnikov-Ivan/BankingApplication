package com.haulmont.bankingApplication.services;

import com.haulmont.bankingApplication.models.Credit;
import com.haulmont.bankingApplication.repositories.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditService {
    @Autowired
    CreditRepository creditRepository;

    public CreditService() {
    }

    public List<Credit> findAll() { return creditRepository.findAll(); }

    public void delete (Credit credit) { creditRepository.delete(credit); }

    public void save (Credit credit) { creditRepository.save(credit); }

    public Optional<Credit> getCredit(Long creditAmount, Double interestRate) {
        Credit credit = new Credit(creditAmount, interestRate);
        return creditRepository.findOne(Example.of(credit));
    }

    public List<Credit> findCreditByAmount(Long creditAmount){
        return creditRepository.findCreditsByAmount(creditAmount);
    }
}
