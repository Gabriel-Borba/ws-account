package com.account.bank.api;


import com.account.bank.Model.ResponseDto;
import com.account.bank.Service.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/bank")
public class AccountApi {
    private static Logger logger = getLogger(AccountApi.class);
    private AccountService accountService;

    @Autowired
    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/accounts")
    public ResponseEntity<?> createPerson() {
        logger.info("Listing Accounts");
        try {
            return ResponseEntity.ok(accountService.getAccounts());
        } catch (ResponseStatusException response) {
            logger.error("Error listing accounts " + response.getMessage());
            logger.error(response.getReason());
            return ResponseEntity.status(response.getStatus()).body(ResponseDto.builder().message(response.getMessage()).build());
        }
    }
}
