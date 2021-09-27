package com.haulmont.bankingApplication.repositories;

import com.haulmont.bankingApplication.models.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, UUID> {

    @Query("select p from CreditOffer p where p.bankId =:bankID")
    List<CreditOffer> findAllOffersForClient(@Param("bankID") long bankID);

    @Query("delete from CreditOffer p where p.bankId =:bankID")
    void deleteOffersForClient(@Param("bankID") long bankID);
}
