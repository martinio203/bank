package example.bank.repository;

import example.bank.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        userRepository.save(user);
    }

    @Test
    void findUserById() {
        User user = userRepository.findById(1L).get();
        System.out.println(user);
    }

    @Test
    void deleteUserById() {
        userRepository.deleteById(1L);
    }
}