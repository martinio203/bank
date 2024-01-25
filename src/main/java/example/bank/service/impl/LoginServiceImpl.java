package example.bank.service.impl;

import example.bank.entity.User;
import example.bank.repository.UserRepository;
import example.bank.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@RequiredArgsConstructor
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
                            Model model) {

        User userRegistered = findByUsername(user.getUsername());

        if (userRegistered == null){
            model.addAttribute("username", "Username does not exist");
            return "html/login";
        }
        if (!correctPassword(user, userRegistered)){
            model.addAttribute("passwordError", "Password is incorrect");
            return "html/login";
        }
        redirectAttributes.addFlashAttribute("username", user.getUsername());
        return "redirect:/home";
    }

    private boolean correctPassword(User user, User userRegistered){
        return bCryptPasswordEncoder.matches(user.getPassword(), userRegistered.getPassword());
    }
}
