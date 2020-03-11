package com.account.bank.service;

import com.account.bank.Model.AccountDto;
import com.account.bank.Model.PersonDto;
import com.account.bank.Model.entity.AccountEntity;
import com.account.bank.factory.AccountFactory;
import com.account.bank.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountServiceImplTests {

    @Mock
    AccountRepository accountRepository;

    private static final int AGENCY = 112412;
    private static final String ACCOUNT_NUMBER = "231312312321";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mustReturnSuccessWhenPersonIsSaved() {
        PersonDto personDto = buildMockPerson();
        AccountEntity accountEntity = AccountFactory.buildPersonDtoToAccountEntity(personDto, ACCOUNT_NUMBER, AGENCY);
        when(accountRepository.save(accountEntity)).thenReturn(new AccountEntity());
        //When
        accountRepository.save(accountEntity);
        //Then
        verify(accountRepository, times(1)).save(accountEntity);
    }


    @Test
    public void mustReturnDataAccessExceptionWhenPersonIsNotSaved() {
        //Given

        PersonDto personDto = buildMockPerson();
        AccountEntity accountEntity = AccountFactory.buildPersonDtoToAccountEntity(personDto, ACCOUNT_NUMBER, AGENCY);
        when(accountRepository.save(accountEntity)).thenThrow(new DataAccessException("Error For saving entity") {
        });
        //When
        Throwable exception = assertThrows(DataAccessException.class, () -> accountRepository.save(accountEntity));
        //Then
        assertEquals("Error For saving entity", exception.getMessage());
    }


    private PersonDto buildMockPerson() {
        return PersonDto.builder().type("PF").id(4L).documentNumber("12345678901").score(5).name("Alberto Silva").build();
    }


}
