package com.example.test_driven_development.itTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowDeletingTaskWhenUserIsAdmin() throws Exception{
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/api/tasks/42")
                .with(SecurityMockMvcRequestPostProcessors.user("duke").roles("ADMIN"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()) 
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
