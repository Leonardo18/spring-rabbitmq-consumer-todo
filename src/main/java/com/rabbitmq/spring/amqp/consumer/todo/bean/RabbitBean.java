package com.rabbitmq.spring.amqp.consumer.todo.bean;

import com.rabbitmq.spring.amqp.consumer.todo.config.RabbitConfig;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitBean {

    private ConnectionFactory connectionFactory;
    private RabbitConfig rabbitConfig;
    private static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    private static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    private static final Boolean DEAD_LETTER_QUEUE_DURABLE = true;
    private static final Boolean DEAD_LETTER_QUEUE_EXCLUSIVE = false;
    private static final Boolean DEAD_LETTER_QUEUE_AUTO_DELETE = false;

    public RabbitBean(ConnectionFactory connectionFactory, RabbitConfig rabbitConfig) {
        this.connectionFactory = connectionFactory;
        this.rabbitConfig = rabbitConfig;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() { return new RabbitAdmin(connectionFactory); }

    @Bean
    public DirectExchange todoExchange() {
        DirectExchange exchange = new DirectExchange(rabbitConfig.getRabbitExchange());
        rabbitAdmin().declareExchange(exchange);
        return exchange;
    }

    @Bean
    public Queue createDeadLetter() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(X_DEAD_LETTER_EXCHANGE, rabbitConfig.getRabbitExchange());
        arguments.put(X_DEAD_LETTER_ROUTING_KEY, rabbitConfig.getRabbitDeadLetterQueue());
        Queue queue = new Queue(rabbitConfig.getRabbitDeadLetterQueue(), DEAD_LETTER_QUEUE_DURABLE, DEAD_LETTER_QUEUE_EXCLUSIVE, DEAD_LETTER_QUEUE_AUTO_DELETE, arguments);
        queue.setAdminsThatShouldDeclare(rabbitAdmin());
        rabbitAdmin().declareQueue(queue);
        rabbitAdmin().declareBinding(BindingBuilder.bind(queue).to(todoExchange()).with(rabbitConfig.getRabbitDeadLetterQueue()));
        return queue;
    }
}
