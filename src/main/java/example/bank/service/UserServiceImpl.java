package example.bank.service;

import example.bank.dto.UserDto;
import example.bank.entity.User;
import example.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
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

        User existing = findByUsername(userDto.getUsername());
        if (existing != null){
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
