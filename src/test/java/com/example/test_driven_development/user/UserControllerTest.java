package com.example.test_driven_development.user;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnAllUsersForUnauthenticatedUsers() throws Exception{
        when(userService.getAllUsers()).thenReturn(List.of(new User(1L,"Duke")));
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .with(SecurityMockMvcRequestPostProcessors.user("foo").password("bar"))
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Duke"));
    }

    @Test
    void shouldAllowCreationForUnauthenticatedUser() throws Exception{
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/users")
                    .with(SecurityMockMvcRequestPostProcessors.user("john").password("doe"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Duke\"}")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.header().exists("location"))
            .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("Duke")));

        verify(userService).storeNewUser(any(User.class));
    }
}
