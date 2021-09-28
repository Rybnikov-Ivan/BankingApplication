package com.haulmont.bankingApplication.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "credits")
public class Credit {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "creditLimit")
    private Long creditLimit;

    @NotNull
    @Column(name = "interestRate")
    private Double interestRate;

    public Credit(){}

    public Credit(Long creditLimit, Double interestRate) {
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }
}
