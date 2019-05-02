package com.rabbitmq.spring.amqp.consumer.todo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SpringAmqpConsumerTodoApplication.class)
@ComponentScan(basePackages = {"br.com.agibank.noname.store.consumer"})
public class TestConfig { }
