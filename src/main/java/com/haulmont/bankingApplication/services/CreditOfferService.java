package com.haulmont.bankingApplication.services;

import com.haulmont.bankingApplication.models.CreditOffer;
import com.haulmont.bankingApplication.repositories.CreditOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditOfferService {
    @Autowired
    CreditOfferRepository creditOfferRepository;

    public CreditOfferService(){}

    public void save(CreditOffer creditOffer) {
        creditOfferRepository.save(creditOffer);
    }

    public List<CreditOffer> findOffersForClient(Long bankId) {
        return creditOfferRepository.findAllOffersForClient(bankId);
    }

    public void deleteAllOffersForClient(Long bankID) {
        creditOfferRepository.deleteOffersForClient(bankID);
    }
}
