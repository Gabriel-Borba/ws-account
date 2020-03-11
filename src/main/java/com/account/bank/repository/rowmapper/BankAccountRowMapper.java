package com.account.bank.repository.rowmapper;

import com.account.bank.Model.BankAccountResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountRowMapper implements RowMapper<BankAccountResponse> {

    @Override
    public BankAccountResponse mapRow(ResultSet resultSet, int i) throws SQLException {
        return BankAccountResponse.builder().agency(resultSet.getInt("AGENCY"))
                .creditLimit(resultSet.getDouble("CREDIT"))
                .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                .specialLimit(resultSet.getDouble("SPECIAL"))
                .type(resultSet.getString("TYPE"))
                .build();
    }
}
