package com.example.test_driven_development.task;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    public Long createTask(String title){
        System.out.println("Creating a new task with title : " + title);
        return Math.abs(ThreadLocalRandom.current().nextLong());
    }

    public void deleteTask(Long taskId){
        System.out.println("Deleting task with id : " + taskId);
    }

}
