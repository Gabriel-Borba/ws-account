package com.account.bank.Model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PersonDto implements Serializable {
    private String name;
    private String type;
    private String documentNumber;
    private int score;
    private Long id;
}
