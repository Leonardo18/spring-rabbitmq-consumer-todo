package com.rabbitmq.spring.amqp.consumer.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = { "com.rabbitmq.spring.amqp.consumer.todo" })
public class SpringAmqpConsumerTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAmqpConsumerTodoApplication.class, args);
	}

}
