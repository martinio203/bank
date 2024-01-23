package example.bank.controller;

import example.bank.dto.UserDto;
import example.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setConfirmPassword("testPassword");
        Mockito.when(userService.registerUser(Mockito.argThat(input -> input.getPassword().equals(input.getConfirmPassword())), Mockito.any(), Mockito.any()))
                .thenReturn("redirect:/login");

        Mockito.when(userService.registerUser(Mockito.argThat(input -> !input.getPassword().equals(input.getConfirmPassword())), Mockito.any(), Mockito.any()))
                .thenReturn("html/register");
    }

    @Test
    void shouldReturnRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("html/register"));
    }

    @Test
    void shouldRegisterUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("username", "testUser")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void passwordNotMatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("username", "testUser")
                        .param("password", "testPassword")
                        .param("confirmPassword", "tesstPassword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("html/register"));
    }
}