package com.example.test_driven_development.todo;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoRepository todoreRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllTodos() throws Exception {

        var todos = List.of(new Todo("Test-1", false), new Todo("Test-2", false));
        when(todoreRepository.findAll()).thenReturn(todos);
        // MvcResult mvcResult = mockMvc.perform(get("/api/todos")).andReturn();
        // assertEquals(200, mvcResult.getResponse().getStatus());

        // mockMvc.perform(get("/api/todos"))
        //         .andExpect(status().isOk())
        //         .andExpect(content().json(objectMapper.writeValueAsString(todos)));

        mockMvc.perform(get("/api/todos"))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(todos)));
    }
}
