package com.rabbitmq.spring.amqp.consumer.todo.service;

import com.rabbitmq.spring.amqp.consumer.todo.Model.TodoModel;
import com.rabbitmq.spring.amqp.consumer.todo.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    private TodoService todoService;
    private RabbitTemplate rabbitTemplate;
    private RabbitConfig rabbitConfig;
    private static final Logger logger = LoggerFactory.getLogger(RabbitService.class);

    public RabbitService(TodoService todoService, RabbitTemplate rabbitTemplate, RabbitConfig rabbitConfig) {
        this.todoService = todoService;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitConfig = rabbitConfig;
    }

    public void todoProcess(TodoModel todoModel) {
        try {
            logger.info("[TODO] Processing todo from queue");
            todoService.throwExceptionTodo(todoModel);
        } catch (Exception e){
            logger.error(e.getMessage());
            logger.error("The following todo will be send to dead letter: " + todoModel.toString());
            feedDeadLetter(todoModel);
            logger.error("The following todo was sent to dead letter: " + todoModel.toString());
        }
    }

    private void feedDeadLetter(TodoModel todoModel){
        rabbitTemplate.convertAndSend(rabbitConfig.getRabbitExchange(), rabbitConfig.getRabbitDeadLetterQueue(), todoModel.toString());
    }
}
