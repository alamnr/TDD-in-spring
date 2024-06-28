package com.example.test_driven_development.dashBoard;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DashboardController.class)
public class DashBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashBoardService dashBoardService;

    @Test
    void shouldReturnViewWithPrefilledData() throws Exception{
        when(dashBoardService.getAnalyticsGraphData()).thenReturn(new Integer[]{25,14});

        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/dashboard")
                .with(SecurityMockMvcRequestPostProcessors.user("jane").password("tarjan"))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("myDashboard"))
            .andExpect(MockMvcResultMatchers.model().attribute("user", "Duke"))
            .andExpect(MockMvcResultMatchers.model().attribute("analyticsGraph", Matchers.arrayContaining(25,14)))
            .andExpect(MockMvcResultMatchers.model().attributeExists("quickNote"));
        
    }
}
