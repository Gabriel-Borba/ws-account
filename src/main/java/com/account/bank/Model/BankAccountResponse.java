package com.account.bank.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BankAccountResponse {
    private Double creditLimit;
    private Double specialLimit;
    private String accountNumber;
    private int agency;
    private String type;

}
