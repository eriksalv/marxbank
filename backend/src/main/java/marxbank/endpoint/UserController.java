package marxbank.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import marxbank.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import marxbank.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Transactional
    public List<User> findAll() {
        List<User> result = new ArrayList<User>();
        userRepository.findAll().forEach(u -> result.add(u));
        return result;
    }

    @GetMapping("/{id}")
    @Transactional
    public User findById(@PathVariable Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
    }

}
