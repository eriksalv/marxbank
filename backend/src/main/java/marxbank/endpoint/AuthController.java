package marxbank.endpoint;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import marxbank.API.LogInRequest;
import marxbank.API.LogInResponse;
import marxbank.API.SignUpRequest;
import marxbank.API.UserResponse;
import marxbank.model.User;
import marxbank.repository.TokenRepository;
import marxbank.repository.UserRepository;
import marxbank.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private AuthService authService;

    @Autowired
    public AuthController(UserRepository userRepository, TokenRepository tokenRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.authService = authService;
    }


    @PostMapping("/login")
    @Transactional
    public LogInResponse login(@RequestBody LogInRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        
        // TODO: legge til egne exceptions her
        User user = userRepository.findByUsername(username).orElseThrow(IllegalStateException::new);
        System.out.println(password);
        System.out.println(user.getPassword());
        System.out.println(user.getPassword().equals(password));
        if (!user.getPassword().equals(password)) throw new IllegalStateException();

        String token = authService.createTokenForUser(user);

        return new LogInResponse(token, new UserResponse(user));
    }

    @PostMapping("/signup")
    @Transactional
    public LogInResponse signUp(@RequestBody SignUpRequest request) {
        System.out.println(request.getUsername());
        User user = request.createUser();
        if (!user.validate()) throw new IllegalStateException("User values are not valid");
        if (userRepository.findByUsername(user.getUsername()).isPresent()) throw new IllegalStateException("User already exists");

        userRepository.save(user);
        String token = authService.createTokenForUser(user);
        return new LogInResponse(token, new UserResponse(user));
    }

}
