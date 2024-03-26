package example.bank.service;

import example.bank.dto.UserDto;
import example.bank.model.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

@Service
public interface RegisterService {
    void saveUser(UserDto userDto);
    User findByUsername(String username);
    String registerUser(UserDto userDto, BindingResult bindingResult, Model model);

}
