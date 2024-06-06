package com.example.test_driven_development.todo;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;



public class TodoHamcrestTest {
    
    @Test
    void shouldCreateNewTodo(){
        var todo1 = new Todo("Test",true);
        var todo2 = new Todo("Test",true);

        assertThat(todo1,equalTo(todo2));
    }
}
