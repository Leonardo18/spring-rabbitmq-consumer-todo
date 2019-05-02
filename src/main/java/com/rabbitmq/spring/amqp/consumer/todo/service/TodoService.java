package com.rabbitmq.spring.amqp.consumer.todo.service;

import com.rabbitmq.spring.amqp.consumer.todo.Model.TodoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    void throwExceptionTodo(TodoModel todoModel) throws Exception {
        throw new Exception("Exception to test dead letter");
    }
}
