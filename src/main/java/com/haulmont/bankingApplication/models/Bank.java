package com.haulmont.bankingApplication.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Credit bankCredit;

    public Bank(){}

    public Bank(Client client, Credit bankCredit) {
        this.client = client;
        this.bankCredit = bankCredit;
    }
}
