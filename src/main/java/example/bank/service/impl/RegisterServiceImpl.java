package example.bank.service.impl;

import example.bank.dto.UserDto;
import example.bank.entity.User;
import example.bank.repository.UserRepository;
import example.bank.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setBalance(BigDecimal.valueOf(100));
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
