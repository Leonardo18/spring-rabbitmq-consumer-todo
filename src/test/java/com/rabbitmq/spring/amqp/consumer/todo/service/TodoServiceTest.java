package com.rabbitmq.spring.amqp.consumer.todo.service;

import com.rabbitmq.spring.amqp.consumer.todo.Model.TodoModel;
import com.rabbitmq.spring.amqp.consumer.todo.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Import;

@RunWith(MockitoJUnitRunner.class)
@Import(TestConfig.class)
public class TodoServiceTest {

    private TodoService todoService;

    @Before
    public void setUp() throws Exception {
        todoService = new TodoService();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionTodoWithSuccess() throws Exception {
        todoService.throwExceptionTodo(buildTodoModel());
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
