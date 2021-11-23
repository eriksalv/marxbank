package marxbank.endpoint;

import javax.transaction.Transactional;

import marxbank.API.EditUserRequest;
import marxbank.API.UserResponse;
import marxbank.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import marxbank.repository.UserRepository;
import marxbank.service.AuthService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final AuthService authService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
        encoder = new BCryptPasswordEncoder();
    }

    /**
     * Finds a specific user by id (and token)
     * @param token token of logged in user
     * @param id id of user
     * @return user response with http status (200) if request is valid,
     * or null with http status 403 (forbidden) if token is null or user
     * is null, or null with http status 404 (not found) if the provided
     * id doesn't match the actual id.
     */
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<UserResponse> findById(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token, @PathVariable Long id) {
        if (token == null || authService.getUserFromToken(token) == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized access");

        if (!authService.getUserFromToken(token).getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(authService.getUserFromToken(token)));
    }

    @PostMapping("/{id}/edit")
    @Transactional
    public ResponseEntity<UserResponse> editUser(@RequestHeader(name = "Authorization", required = false) @Nullable String token, @PathVariable Long id, @RequestBody EditUserRequest request) {
        if (token == null || authService.getUserFromToken(token) == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized access");

        if (!authService.getUserFromToken(token).getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
        }
        User user = authService.getUserFromToken(token);

        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect password");
        }
        
        if (!request.getUsername().equals(user.getUsername()) && userRepository.findByUsername(request.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");

        try {
            user.setUsername(request.getUsername());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user));        
    }

}
