package com.account.bank.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LimitDto {
    private Double creditLimit;
    private Double specialLimit;
}
