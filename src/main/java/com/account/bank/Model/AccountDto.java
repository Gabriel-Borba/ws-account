package com.account.bank.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDto {
private String accountNumber;
private Long agency;
}
