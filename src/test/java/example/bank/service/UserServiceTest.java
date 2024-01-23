package example.bank.service;

import example.bank.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private RegisterService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");

        userService.saveUser(userDto);

        verify(userService, times(1)).saveUser(userDto);

    }

    @Test
    void passwordMatch() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setConfirmPassword("testPassword");

        boolean result = userDto.getPassword().equals(userDto.getConfirmPassword());

        Assertions.assertEquals(true, result);
    }

    @Test
    void passwordNotMatch() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setConfirmPassword("testPassword");

        when(userService.registerUser(any(UserDto.class), any(BindingResult.class), any(Model.class)))
                .thenReturn("html/register");

        String result = userService.registerUser(userDto, bindingResult, model);

        verify(userService, times(1)).registerUser(userDto, bindingResult, model);
        Assertions.assertEquals("redirect:/login", result);
    }
}
