package com.account.bank.Model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "account_info")
public class LimitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CREDIT")
    private Double creditLimit;
    @Column(name = "SPECIAL")
    private Double SpecialLimit;
    @Column(name = "ID_ACCOUNT")
    private Long idAccount;
}
