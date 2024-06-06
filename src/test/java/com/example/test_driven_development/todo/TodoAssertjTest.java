package com.example.test_driven_development.todo;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class TodoAssertjTest {
    
    @Test
    void shouldCreateNewTodo(){

        var todo =  new Todo("TEST",true);

        assertThat(todo.name())
                .startsWith("T")
                .endsWith("T")
                .contains("ES")
                .isEqualToIgnoringCase("Test");
    }
}
