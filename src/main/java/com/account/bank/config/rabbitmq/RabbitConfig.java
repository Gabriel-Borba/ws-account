package com.account.bank.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {



    public String accountQueue = "account";


    @Bean
    public Queue pushQueue() {
        return QueueBuilder.durable(accountQueue).build();
    }

    @Bean
    public DirectExchange exchange() {
        String exchange = "bank";
        return (DirectExchange) ExchangeBuilder.directExchange(exchange).durable(true).build();
    }


    @Bean
    public Binding bindingPushQueue() {
        return BindingBuilder.bind(this.pushQueue()).to(this.exchange()).with(this.accountQueue);
    }



}
