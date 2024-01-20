package example.bank.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String displayLoginPage(){
        return "html/login";
    }

    @GetMapping("/register")
    public String displayRegisterPage(){
        return "html/register";
    }
}
