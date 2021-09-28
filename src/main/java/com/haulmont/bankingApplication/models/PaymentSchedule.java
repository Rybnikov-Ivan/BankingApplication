package com.haulmont.bankingApplication.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "paymentSchedule")
public class PaymentSchedule {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "dateOfPayment")
    private Date dateOfPayment;

    @NotNull
    @Column(name = "paymentAmount")
    private Double paymentAmount;

    @NotNull
    @Column(name = "repaymentAmountMainPart")
    private Double repaymentAmountMainPart;

    @NotNull
    @Column(name = "percentRepayment")
    private Double percentRepayment;

    public PaymentSchedule(){}

    public PaymentSchedule(Date dateOfPayment, Double paymentAmount, Double percentRepayment, Double repaymentAmountMainPart) {
        this.dateOfPayment = dateOfPayment;
        this.paymentAmount = paymentAmount;
        this.percentRepayment = percentRepayment;
        this.repaymentAmountMainPart = repaymentAmountMainPart;
    }
}
