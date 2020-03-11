package com.account.bank.Model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "account")
@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACCOUNT")
    private Long id;
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name = "AGENCY")
    private int agency;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "ID_PERSON")
    private Long idPerson;
}
