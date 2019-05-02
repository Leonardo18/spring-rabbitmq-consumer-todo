package com.rabbitmq.spring.amqp.consumer.todo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

    @Value("${spring.rabbit.url}")
    private String rabbitUrl;

    @Value("${spring.rabbit.exchange}")
    private String rabbitExchange;

    @Value("${spring.rabbit.todo.queue}")
    private String rabbitQueue;

    @Value("${spring.rabbit.todo.dead-letter}")
    private String rabbitDeadLetterQueue;

    @Value("${spring.rabbit.user}")
    private String rabbitUser;

    @Value("${spring.rabbit.pass}")
    private String rabbitPass;

    public String getRabbitUrl() { return rabbitUrl; }

    public String getRabbitExchange() { return rabbitExchange; }

    public String getRabbitQueue() { return rabbitQueue; }

    public String getRabbitDeadLetterQueue() { return rabbitDeadLetterQueue; }

    public String getRabbitUser() { return rabbitUser; }

    public String getRabbitPass() { return rabbitPass; }
}
