package com.rabbitmq.spring.amqp.consumer.todo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.spring.amqp.consumer.todo.Model.TodoModel;
import com.rabbitmq.spring.amqp.consumer.todo.consumer.dto.TodoDto;
import com.rabbitmq.spring.amqp.consumer.todo.service.RabbitService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TodoRabbitConsumer {

    private RabbitService rabbitService;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(TodoRabbitConsumer.class);

    public TodoRabbitConsumer(RabbitService rabbitService, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.rabbitService = rabbitService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbit.todo.queue}"),
            exchange = @Exchange("${spring.rabbit.exchange}"),
            key = "${spring.rabbit.todo.queue}"
    ))
    public void receiveCreateEvent(byte[] todoDtoArray) {
        try {
            TodoDto todoDto = getTodoDtoByBytesArray(todoDtoArray);
            logger.info(String.format("[TODO] - Message received: %s", todoDto.toString()));
            rabbitService.todoProcess(convertTodoDtoToTodoModel(todoDto));
        } catch (Exception exception) {
            logger.error(String.format("[TODO] Error to get message from queue. Detailed Error: %s", exception.getMessage()));
            throw new AmqpException("Error in rabbit");
        }
    }

    private TodoDto getTodoDtoByBytesArray(byte[] todoDtoArray) throws Exception{
        return objectMapper.readValue(todoDtoArray, TodoDto.class);
    }

    private TodoModel convertTodoDtoToTodoModel(TodoDto todoDto) {
        return modelMapper.map(todoDto, TodoModel.class);
    }
}
