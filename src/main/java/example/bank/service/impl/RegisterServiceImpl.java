package example.bank.service.impl;

import example.bank.dto.UserDto;
import example.bank.model.User;
import example.bank.repository.UserRepository;
import example.bank.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setBalance(new BigDecimal("100"));
        user.setRole("USER");
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String registerUser(UserDto userDto,
                             BindingResult bindingResult,
                             Model model){

        User userExist = findByUsername(userDto.getUsername());
        if (userExist != null){
            bindingResult.rejectValue("username", "Username already exists");
            model.addAttribute("usernameError", "Username already exists");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
            bindingResult.rejectValue("confirmPassword", "Passwords do not match");
            model.addAttribute("passwordError", "Passwords do not match");
        }
        if (bindingResult.hasErrors()){
            return "html/register";
        }
        saveUser(userDto);
        return "redirect:/login";
    }
}
