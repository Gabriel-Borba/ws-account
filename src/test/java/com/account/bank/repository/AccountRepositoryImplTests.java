package com.account.bank.repository;

import com.account.bank.Model.BankAccountResponse;
import com.account.bank.Model.PersonDto;
import com.account.bank.Model.entity.AccountEntity;
import com.account.bank.factory.AccountFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountRepositoryImplTests {

    @Mock
    AccountRepositoryImpl accountRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mustReturnNewBankAccountList() {
        //Given
        List<BankAccountResponse> accounts = new ArrayList<>();
        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        //When
        accountRepository.getAllAccounts();
        //Then
        verify(accountRepository, times(1)).getAllAccounts();
    }


    @Test
    public void mustReturnResponseStatusExceptionWhenListIsEmpty() {
        //Given
        when(accountRepository.getAllAccounts()).thenThrow(new ResponseStatusException(HttpStatus.NO_CONTENT) {
        });
        //When
        Throwable exception = assertThrows(ResponseStatusException.class, () -> accountRepository.getAllAccounts());
        //Then
        assertEquals(String.valueOf(HttpStatus.NO_CONTENT), exception.getMessage());
    }

}
