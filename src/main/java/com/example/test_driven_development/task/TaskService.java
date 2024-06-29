package com.example.test_driven_development.task;

public interface TaskService {

    Long createTask(String asText);

    void deleteTask(Long taskId);

}
