package com.account.bank.factory;

import com.account.bank.Model.LimitDto;
import com.account.bank.Model.PersonDto;
import com.account.bank.Model.entity.AccountEntity;
import com.account.bank.Model.entity.LimitEntity;

public class AccountFactory {
    public static AccountEntity buildPersonDtoToAccountEntity(PersonDto personDto, String accountNumber, int agency) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(accountNumber);
        accountEntity.setType(personDto.getType().equals("PF") ? "C" : "E");
        accountEntity.setAgency(agency);
        accountEntity.setIdPerson(personDto.getId());
        return accountEntity;
    }

    public static LimitEntity buildLimitEntity(LimitDto limitDto, Long idAccount) {
        LimitEntity limitEntity = new LimitEntity();
        limitEntity.setCreditLimit(limitDto.getCreditLimit());
        limitEntity.setSpecialLimit(limitDto.getSpecialLimit());
        limitEntity.setIdAccount(idAccount);
        return limitEntity;
    }

    public static LimitDto buildLimitDto(Double creditLimit, Double specialLimit) {
        return LimitDto.builder().creditLimit(creditLimit).specialLimit(specialLimit).build();
    }
}
