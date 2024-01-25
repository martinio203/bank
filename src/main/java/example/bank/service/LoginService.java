package example.bank.service;

import example.bank.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public interface LoginService {
    User findByUsername(String username);
    String loginUser(User user, RedirectAttributes redirectAttributes, Model model);
}
