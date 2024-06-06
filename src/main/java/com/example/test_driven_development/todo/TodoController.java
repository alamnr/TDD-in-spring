package com.example.test_driven_development.todo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TodoController {
    
    
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @GetMapping("/api/todos")
    ResponseEntity<List<Todo>> findAll(){
        return new ResponseEntity<>(todoRepository.findAll(),HttpStatus.OK);
    }
}
