package com.haulmont.bankingApplication.services;

import com.haulmont.bankingApplication.models.CreditOffer;
import com.haulmont.bankingApplication.repositories.CreditOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditOfferService {
    @Autowired
    CreditOfferRepository creditOfferRepository;

    public CreditOfferService(){}

    public void save(CreditOffer creditOffer) {
        creditOfferRepository.save(creditOffer);
    }

    public List<CreditOffer> findOffersForClient(UUID bankId) {
        return creditOfferRepository.findAllOffersForClient(bankId);
    }

    public void deleteAllOffersForClient(UUID bankID) {
        creditOfferRepository.deleteOffersForClient(bankID);
    }
}
