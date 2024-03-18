package example.bank.controller;

import example.bank.dto.UserDto;

import example.bank.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("userDto") UserDto userDto){
        return "html/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") @Valid UserDto userDto,
                               BindingResult bindingResult,
                               Model model){
        return registerService.registerUser(userDto, bindingResult, model);
    }
}
