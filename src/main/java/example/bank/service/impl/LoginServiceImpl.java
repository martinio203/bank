package example.bank.service.impl;

import example.bank.entity.User;
import example.bank.repository.UserRepository;
import example.bank.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;


@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String loginUser(User user,
                            RedirectAttributes redirectAttributes,
                            BindingResult bindingResult,
                            Model model) {

        User userRegistered = findByUsername(user.getUsername());
        if (userRegistered == null){
            System.out.println("found user: " + userRegistered.getUsername());
            bindingResult.rejectValue("username", "Username does not exist");
            model.addAttribute("username", "Username does not exist");
        }
        if (!correctPassword(user, Objects.requireNonNull(userRegistered))){
            bindingResult.rejectValue("password", "Password is incorrect");
            model.addAttribute("passwordError", "Password is incorrect");
        }
        if (bindingResult.hasErrors()){
            return "html/login";
        }
        model.addAttribute("username", user.getUsername());
        redirectAttributes.addFlashAttribute("username", user.getUsername());
        return "redirect:/home";
    }

    @Override
    public boolean correctPassword(User user, User userRegistered) {
        return bCryptPasswordEncoder.matches(user.getPassword(), userRegistered.getPassword());
    }

//    private boolean correctPassword(User user, User userRegistered){
//        return bCryptPasswordEncoder.matches(user.getPassword(), userRegistered.getPassword());
//    }
}
