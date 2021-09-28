package com.haulmont.bankingApplication.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "creditOffers")
public class CreditOffer {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditId")
    private Credit credit;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentScheduleId")
    private PaymentSchedule paymentSchedule;

    @NotNull
    @Column(name = "creditAmount")
    private Long creditAmount;

    @NotNull
    @Column(name = "bankId")
    private long bankId;

    public CreditOffer(){}

    public CreditOffer(Client client, Credit credit, PaymentSchedule paymentSchedule, Long creditAmount, long bankId) {
        this.client = client;
        this.credit = credit;
        this.paymentSchedule = paymentSchedule;
        this.creditAmount = creditAmount;
        this.bankId = bankId;
    }
}
