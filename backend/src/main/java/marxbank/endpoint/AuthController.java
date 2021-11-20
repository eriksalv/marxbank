package marxbank.endpoint;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import marxbank.repository.TokenRepository;
import marxbank.repository.UserRepository;
import marxbank.service.AuthService;

/**
 * Contains all requests connected to login/logout and signup.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private AuthService authService;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public AuthController(UserRepository userRepository, AuthService authService, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.tokenRepository = tokenRepository;
        encoder = new BCryptPasswordEncoder();
    }

    /**
     * Request to login with existing user. Creates token when user successfully
     * logs in.
     * @param request login request
     * @return login response with http status 200 (ok) if login is successful,
     * or null with http status 400 (bad request) if request is invalid,
     * or null with http status 404 (not found) if username does not match any
     * registered users, or null with http status 403 (forbidden) if password
     * is incorrect. 
     */
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LogInResponse> login(@RequestBody LogInRequest request) {

        if (request.getUsername().length() < 4 || request.getPassword().length() < 4)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (!userRepository.findByUsername(request.getUsername()).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        User user = userRepository.findByUsername(request.getUsername()).get();

        if (!encoder.matches(request.getPassword(), user.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        String token = authService.createTokenForUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(new LogInResponse(token, new UserResponse(user)));
    }

    @GetMapping("/login")
    @Transactional
    public ResponseEntity<UserResponse> login(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token) {

        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (authService.getUserFromToken(token) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse(userRepository.findByToken_Token(AuthService.removeBearer(token)).get()));
    }

    /**
     * Request to signup as a user. Encodes password before storing in user repository,
     * and creates a token for the created user.
     * @param request signup request
     * @return a login response with http status 201 (created) if the request is valid,
     * or null with http status 400 (bad request) if request is invalid,
     * or null with http status 409 (conflict) if username is already registered.
     */
    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<LogInResponse> signUp(@RequestBody SignUpRequest request) {
        User user = request.createUser();
        if (!user.validate())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        user.setPassword(encoder.encode(request.getPassword()));

        userRepository.save(user);
        String token = authService.createTokenForUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LogInResponse(token, new UserResponse(user)));
    }

    /**
     * Logs a user out by removing the users token.
     * @param token token belonging to the logged in user
     * @return null with http status 200 (ok) if token is valid,
     * or null with http status 403 (forbidden) if token is null,
     * or null with http status 400 (bad request) if token is not in repository. 
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token) {
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (!tokenRepository.findByToken(token).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        authService.removeToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
