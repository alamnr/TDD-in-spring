package com.example.test_driven_development.task;

import org.hamcrest.Matchers;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.test_driven_development.security.SecurityConfig;

@Import(SecurityConfig.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void shouldrejectCreatingTaskWhenUserIsAnonymousWithoutCsrf() throws Exception{
        this.mockMvc
                .perform(
                    MockMvcRequestBuilders.post("/api/task")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"taskTitle\":\"Learn MockMvc\"}")

                )
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldrrRejectCrestingTaskWhenUserIsAnonymousWithCsrf() throws Exception{
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"taskTitle\":\"Learn MockMvc\"}")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldReturnLocationOfTaskWhenUserIsAuthenticatedAndCreatesTask() throws Exception{
        when(taskService.createTask(anyString())).thenReturn(42L);

        this.mockMvc
                .perform(
                    MockMvcRequestBuilders.post("/api/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"taskTitle\":\"Learn MockMvc\"}")
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                    .with(SecurityMockMvcRequestPostProcessors.user("duke"))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("42")));

    }

    @Test
    void shouldDeleteTasksWhenUserIsAdmin() throws Exception{
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/api/tasks/42")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("duke").roles("ADMIN"))
            )
            .andExpect(MockMvcResultMatchers.status().isOk());

            verify(taskService).deleteTask(42L);
    }

    @Test
    @WithMockUser("duke")
    void shouldRejectDeletingWhenUserLacksAdminRole() throws Exception
    {
        this.mockMvc
                .perform(
                    MockMvcRequestBuilders.delete("/api/tests/42")
                    .with(SecurityMockMvcRequestPostProcessors.csrf())

                )

                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
