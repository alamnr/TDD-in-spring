package com.example.test_driven_development.todo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {

    private static final Logger log = getLogger(TodoRepository.class);
    List<Todo> todos = new ArrayList<>();

    public TodoRepository(){
        this.todos = List.of(new Todo("Test-1", false),new Todo("Test-2",true));
    }

    List<Todo> findAll(){
        log.info("TodoRepository.findAll called ...");
        return todos;
    }

}
