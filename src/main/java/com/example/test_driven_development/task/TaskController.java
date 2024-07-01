package com.example.test_driven_development.task;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService  taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> createNewTask(@RequestBody JsonNode payload, UriComponentsBuilder uriComponentBuilder){
        
        Long taskId = this.taskService.createTask(payload.get("taskTitle").asText());
        return ResponseEntity
                .created(uriComponentBuilder.path("/api/task/{taskId}").build(taskId))
                .build();
    }

    @DeleteMapping
    //@RolesAllowed("ADMIN")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        this.taskService.deleteTask(taskId);
    }
}
