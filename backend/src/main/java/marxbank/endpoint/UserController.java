package marxbank.endpoint;

import javax.transaction.Transactional;

import marxbank.API.EditUserRequest;
import marxbank.API.UserResponse;
import marxbank.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (!authService.getUserFromToken(token).getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(authService.getUserFromToken(token)));
    }

    @PostMapping("/{id}/edit")
    @Transactional
    public ResponseEntity<UserResponse> editUser(@RequestHeader(name = "Authorization", required = false) @Nullable String token, @PathVariable Long id, @RequestBody EditUserRequest request) {
        if (token == null || authService.getUserFromToken(token) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (!authService.getUserFromToken(token).getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        User user = authService.getUserFromToken(token);
        
        try {
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user));        
    }

}
