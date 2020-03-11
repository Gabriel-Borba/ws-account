package com.account.bank.Service.Impl;

import com.account.bank.Model.BankAccountResponse;
import com.account.bank.Model.LimitDto;
import com.account.bank.Model.PersonDto;
import com.account.bank.Model.entity.AccountEntity;
import com.account.bank.Service.AccountService;
import com.account.bank.factory.AccountFactory;
import com.account.bank.repository.AccountRepository;
import com.account.bank.repository.AccountRepositoryImpl;
import com.account.bank.repository.LimitRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AccountServiceImpl implements AccountService {
    private static Logger logger = getLogger(PersonConsumerServiceImpl.class);
    private AccountRepository accountRepository;
    private LimitRepository limitRepository;
    private AccountRepositoryImpl accountRepositoryImpl;
    @Value("${bank.parameters.score.bronze}")
    private static int scoreBronze;
    @Value("${bank.parameters.score.silver}")
    private int scoreSilver;
    @Value("${bank.parameters.score.gold}")
    private int scoreGold;
    @Value("${bank.parameters.limit.credit.bronze}")
    private Double limitCreditBronze;
    @Value("${bank.parameters.limit.credit.silver}")
    private Double limitCreditSilver;
    @Value("${bank.parameters.limit.credit.gold}")
    private Double limitCreditGold;
    @Value("${bank.parameters.limit.credit.platinum}")
    private Double limitCreditPlatinum;
    @Value("${bank.parameters.limit.special.bronze}")
    private Double limitSpecialBronze;
    @Value("${bank.parameters.limit.special.silver}")
    private Double limitSpecialSilver;
    @Value("${bank.parameters.limit.special.gold}")
    private Double limitSpecialGold;
    @Value("${bank.parameters.limit.special.platinum}")
    private Double limitSpecialPlatinum;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, LimitRepository limitRepository, AccountRepositoryImpl accountRepositoryImpl) {
        this.accountRepository = accountRepository;
        this.limitRepository = limitRepository;
        this.accountRepositoryImpl = accountRepositoryImpl;
    }

    @Override
    public void createAccount(AccountEntity accountEntity, PersonDto personDto) {
        logger.info("Saving new account in database" + accountEntity.toString());
        try {
            AccountEntity savedAccount = accountRepository.save(accountEntity);
            logger.info("Account created sucesfully, creating limits for the new account ");
            saveAccountLimits(personDto, savedAccount);
        } catch (DataAccessException data) {
            logger.error("Error at saving new account in database: " + accountEntity.toString());
            logger.error(data.getMessage());
        }
    }

    public List<BankAccountResponse> getAccounts() {
        return accountRepositoryImpl.getAllAccounts();
    }


    private void saveAccountLimits(PersonDto personDto, AccountEntity savedAccount) {
        try {
            limitRepository.save(AccountFactory.buildLimitEntity(createLimits(personDto), savedAccount.getId()));
        } catch (DataAccessException data) {
            logger.error("Error for saving limits from account number: " + savedAccount.getId());
            logger.error(data.getMessage());
        }
    }

    private LimitDto createLimits(PersonDto personDto) {
        int score = personDto.getScore();
        if (score <= scoreBronze) {
            return AccountFactory.buildLimitDto(limitCreditBronze, limitSpecialBronze);
        } else if (score <= scoreSilver) {
            return AccountFactory.buildLimitDto(limitCreditSilver, limitSpecialSilver);
        } else if (score <= scoreGold) {
            return AccountFactory.buildLimitDto(limitCreditGold, limitSpecialGold);
        } else {
            return AccountFactory.buildLimitDto(limitCreditPlatinum, limitSpecialPlatinum);
        }
    }
}
