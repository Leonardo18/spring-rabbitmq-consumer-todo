package com.rabbitmq.spring.amqp.consumer.todo.service;

import com.rabbitmq.spring.amqp.consumer.todo.Model.TodoModel;
import com.rabbitmq.spring.amqp.consumer.todo.TestConfig;
import com.rabbitmq.spring.amqp.consumer.todo.config.RabbitConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@Import(TestConfig.class)
public class RabbitServiceTest {

    private RabbitService rabbitService;
    private TodoService todoService;
    private RabbitTemplate rabbitTemplate;
    private RabbitConfig rabbitConfig;

    @Before
    public void setUp() throws Exception {
        this.todoService = mock(TodoService.class);
        this.rabbitTemplate = mock(RabbitTemplate.class);
        this.rabbitConfig = mock(RabbitConfig.class);
        this.rabbitService = new RabbitService(todoService, rabbitTemplate, rabbitConfig);
    }

    @Test
    public void shouldTodoProcessWithSuccess() throws Exception {
        doNothing().when(todoService).throwExceptionTodo(any());
        rabbitService.todoProcess(any());
    }

    @Test
    public void shouldTodoProcessWithException() throws Exception {
        doThrow(new Exception("Exception to test dead letter")).when(todoService).throwExceptionTodo(any());
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());
        rabbitService.todoProcess(buildTodoModel());
    }

    private TodoModel buildTodoModel() {
        TodoModel todoModel = new TodoModel();
        todoModel.setName("Todo name");
        todoModel.setDescription("Todo description");
        todoModel.setAuthor("Todo author");
        todoModel.setPriority(2);
        return todoModel;
    }
}
