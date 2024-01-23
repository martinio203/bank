package example.bank.service.impl;

import example.bank.dto.UserDto;
import example.bank.entity.User;
import example.bank.repository.UserRepository;
import example.bank.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String loginUser(@ModelAttribute("user") @Valid User user,
                            RedirectAttributes redirectAttributes, Model model) {

        User userRegistered = findByUsername(user.getUsername());

        if (userRegistered == null){
            model.addAttribute("usernameError", "Username does not exist");
            return "html/login";
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), userRegistered.getPassword())){
            model.addAttribute("passwordError", "Password is incorrect");
            return "html/login";
        }
        redirectAttributes.addFlashAttribute("username", user.getUsername());
        return "redirect:/home";
    }
}
