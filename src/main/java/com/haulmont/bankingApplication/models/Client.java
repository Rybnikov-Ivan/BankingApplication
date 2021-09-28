package com.haulmont.bankingApplication.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @NotNull
    @Column(name = "phoneNumber", unique = true)
    private Long phoneNumber;

    @NotNull
    @Column(name = "passportNumber", unique = true)
    private Long passportNumber;

    @NotNull
    @Column(name = "mail", unique = true)
    private String mail;

    public Client(){

    }

    public Client(String firstname, String surname, String patronymic, Long phoneNumber, Long passportNumber, String mail) {
        this.firstname = firstname;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
        this.mail = mail;
    }
}
