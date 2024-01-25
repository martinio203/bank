package example.bank.controller;

import example.bank.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class LoginControllerTest {
    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void LoginGetRequest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("html/login"));
    }

    @Test
    void UserFailLogin() throws Exception {
        mockMvc.perform(get("/login")
                .param("username", "admin")
                .param("password", "admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("html/login"));
    }

}
