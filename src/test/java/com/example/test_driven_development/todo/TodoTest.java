package com.example.test_driven_development.todo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;



public class TodoTest {

    @Test
    void shouldCreateNewTodo()    {
        var todo =  new Todo("Test",false);
        assertEquals("Test", todo.name());
        assertFalse(todo.complete());
    }

}
