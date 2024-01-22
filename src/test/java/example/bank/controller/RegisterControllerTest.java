package example.bank.controller;

import example.bank.dto.UserDto;
import example.bank.entity.User;
import example.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private Model model;

    @Test
    public void passwordMatchedTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setPassword("password");
        userDto.setConfirmPassword("password");
        when(userService.registerUser(userDto, bindingResult, model)).thenReturn("redirect:/login");
        mockMvc.perform(post("/register")
                .flashAttr("userDto", userDto))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/login"));
    }

}
