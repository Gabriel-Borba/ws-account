package com.account.bank.Service.Impl;

import com.account.bank.Model.PersonDto;
import com.account.bank.Service.AccountService;
import com.account.bank.config.rabbitmq.RabbitConfig;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.concurrent.ThreadLocalRandom;

import static com.account.bank.factory.AccountFactory.*;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class PersonConsumerServiceImpl {
    private static Logger logger = getLogger(PersonConsumerServiceImpl.class);

    private final AmqpAdmin amqpAdmin;
    private RabbitConfig rabbitConfig;
    private AccountService accountService;
    @Value("${bank.parameters.agency}")
    private  int agency;

    @Autowired
    public PersonConsumerServiceImpl(
            AmqpAdmin amqpAdmin,
            RabbitConfig rabbitConfig,
            AccountService accountService) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitConfig = rabbitConfig;
        this.accountService = accountService;
    }

    @PostConstruct
    public void setupRabbit() {
        amqpAdmin.declareExchange(rabbitConfig.exchange());
        amqpAdmin.declareQueue(rabbitConfig.pushQueue());
        amqpAdmin.declareBinding(rabbitConfig.bindingPushQueue());
    }

    @RabbitListener(queues = "${rabbit.bank.account.queue}")
    public void createAccount(String jsonPersonDto) {
        logger.info("Message received from person register: " + jsonPersonDto);
        try {
            PersonDto personDto = new Gson().fromJson(jsonPersonDto, PersonDto.class);
            accountService.createAccount(buildPersonDtoToAccountEntity(personDto, generateNewAccountNumber(), agency), personDto);
            logger.info("Limits created successful, new Account register finished, Welcome: " + personDto.getName());
        } catch (AmqpException ex) {
            logger.error(ex.getMessage());
            logger.error("Error for consuming person for creating a new account" + jsonPersonDto);
        }
    }

    private String generateNewAccountNumber() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

}
