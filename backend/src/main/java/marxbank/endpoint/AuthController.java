package marxbank.endpoint;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import marxbank.API.LogInRequest;
import marxbank.API.LogInResponse;
import marxbank.API.SignUpRequest;
import marxbank.API.UserResponse;
import marxbank.model.User;
import marxbank.repository.UserRepository;
import marxbank.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private UserRepository userRepository;
    private AuthService authService;

    @Autowired
    public AuthController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }


    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LogInResponse> login(@RequestBody LogInRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        
        if (!userRepository.findByUsername(username).isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        User user = userRepository.findByUsername(username).get();
        
        if (!user.getPassword().equals(password)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        String token = authService.createTokenForUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(new LogInResponse(token, new UserResponse(user)));
    }

    @GetMapping("/login")
    @Transactional
    public ResponseEntity<UserResponse> login(@RequestHeader(name = "Authorization", required = false) @Nullable String token) {
        
        if (authService.getUserFromToken(token) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(userRepository.findByToken_Token(token).get()));
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<LogInResponse> signUp(@RequestBody SignUpRequest request) {
        User user = request.createUser();
        if (!user.validate()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        userRepository.save(user);
        String token = authService.createTokenForUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LogInResponse(token, new UserResponse(user)));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = "Authorization", required = false) @Nullable String token) {
        authService.removeToken(token);
        return ResponseEntity.status(HttpStatus.OK).body("{\"signedOut\": \"true\"}");
    }

}
