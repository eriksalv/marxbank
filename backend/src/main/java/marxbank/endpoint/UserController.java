package marxbank.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import marxbank.API.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import marxbank.repository.UserRepository;
import marxbank.service.AuthService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @Autowired
    public UserController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    // @GetMapping
    // @Transactional
    // public List<UserResponse> findAll() {
    //     List<UserResponse> result = new ArrayList<UserResponse>();
    //     userRepository.findAll().forEach(u -> result.add(new UserResponse(u)));
    //     return result;
    // }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<UserResponse> findById(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token, @PathVariable Long id) {
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (authService.getUserFromToken(token) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(authService.getUserFromToken(token)));
    }

}
