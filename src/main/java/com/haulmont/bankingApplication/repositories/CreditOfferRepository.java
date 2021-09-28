package com.haulmont.bankingApplication.repositories;

import com.haulmont.bankingApplication.models.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {

    @Query("select p from CreditOffer p where p.bankId =:bankID")
    List<CreditOffer> findAllOffersForClient(@Param("bankID") Long bankID);

    @Transactional
    @Modifying
    @Query("delete from CreditOffer p where p.bankId =:bankID")
    void deleteOffersForClient(@Param("bankID") Long bankID);
}
