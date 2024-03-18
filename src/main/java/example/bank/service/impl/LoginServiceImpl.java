package example.bank.service.impl;

import example.bank.entity.User;
import example.bank.repository.UserRepository;
import example.bank.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import java.util.Objects;


@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String loginUser(User user,
                            BindingResult bindingResult,
                            Model model) {

        User userRegistered = findByUsername(user.getUsername());
        if (userRegistered == null){
            bindingResult.rejectValue("username", "Username does not exist");
            model.addAttribute("username", "Username does not exist");
            System.out.println("Username does not exist");
        }
        if (!correctPassword(user, Objects.requireNonNull(userRegistered))){
            bindingResult.rejectValue("password", "Password is incorrect");
            model.addAttribute("passwordError", "Password is incorrect");
        }
        if (user.getPassword().equals(userRegistered.getPassword())){
            bindingResult.rejectValue("password", "Password is incorrect");
            model.addAttribute("passwordError", "Password is incorrect");
            System.out.println("Password is incorrect");
        }
        if (bindingResult.hasErrors()){
            System.out.println("Login failed");
            return "html/login";
        }
        model.addAttribute("username", user.getUsername());
        return "redirect:/home";
    }

    @Override
    public boolean correctPassword(User user, User userRegistered) {
        return passwordEncoder.matches(user.getPassword(), userRegistered.getPassword());
    }

}
