package example.bank.controller;

import example.bank.dto.UserDto;

import example.bank.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private  RegisterService registerService;



    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("userDto", new UserDto());
        return "html/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") @Valid UserDto userDto,
                               BindingResult bindingResult,
                               Model model){
        return registerService.registerUser(userDto, bindingResult, model);
    }
}
