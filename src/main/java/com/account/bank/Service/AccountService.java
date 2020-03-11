package com.account.bank.Service;

import com.account.bank.Model.BankAccountResponse;
import com.account.bank.Model.PersonDto;
import com.account.bank.Model.entity.AccountEntity;

import java.util.List;

public interface AccountService {
    public void createAccount(AccountEntity accountEntity, PersonDto personDto);
    List<BankAccountResponse> getAccounts();
}
