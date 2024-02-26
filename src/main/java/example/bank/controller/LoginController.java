package example.bank.controller;

import example.bank.entity.User;
import example.bank.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("user", new User());
        return "html/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") @Valid User user,
                            RedirectAttributes redirectAttributes,
                            BindingResult bindingResult,
                            Model model){
        return loginService.loginUser(user, redirectAttributes, bindingResult, model);
    }

}
