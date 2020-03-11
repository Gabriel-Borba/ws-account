package com.account.bank.repository;

import com.account.bank.Model.BankAccountResponse;
import com.account.bank.Service.Impl.PersonConsumerServiceImpl;
import com.account.bank.repository.rowmapper.BankAccountRowMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class AccountRepositoryImpl {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private static Logger logger = getLogger(AccountRepositoryImpl.class);

    public AccountRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<BankAccountResponse> getAllAccounts() {
        List<BankAccountResponse> accounts;
        try {
            accounts = jdbcTemplate.query(getSqlFromAccount(), new BankAccountRowMapper());
            validateAccountList(accounts);
        } catch (DataAccessException data) {
            logger.error("Error for getting account list ");
            logger.error(data.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error for getting account list");
        }
        return accounts;
    }

    private void validateAccountList(List<BankAccountResponse> accounts) {
        if (accounts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    private String getSqlFromAccount() {
        return " select CREDIT, SPECIAL,AGENCY,TYPE,ACCOUNT_NUMBER from bank.account_info " +
                " inner join account a on account_info.ID_ACCOUNT = a.ID_ACCOUNT ";

    }
}
