package marxbank;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.server.ResponseStatusException;

import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.endpoint.AuthController;
import marxbank.model.User;
import marxbank.repository.UserRepository;
import marxbank.service.AuthService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthTest {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @LocalServerPort
    private int port;

    @Autowired
    private AuthController authController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("test login post")
    public void testLoginPost() {
        // too short username/password
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            LogInRequest badRequest = new LogInRequest("username", "password");
            authController.login(badRequest);
        });

        // create user
        User user = (new SignUpRequest("yeet", encoder.encode("yeet"), "yeet@yeet.com")).createUser();
        userRepository.save(user);

        // wrong password
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            LogInRequest wrongPassword = new LogInRequest("yeet", "password");
            authController.login(wrongPassword);
        });

        // correct password
        LogInRequest correctRequest = new LogInRequest("yeet", "yeet");
        assertEquals(HttpStatus.OK, authController.login(correctRequest).getStatusCode());

        assertAll(
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.FORBIDDEN, thrown2.getStatus())
        );
    }

    @Test
    @DisplayName("test signup")
    public void testSignup() {
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            SignUpRequest badRequest = new SignUpRequest("ye", "ye", "yeet");
            authController.signUp(badRequest);
        });

        User user = (new SignUpRequest("yeet", "yeet", "yeet@email.com")).createUser();
        userRepository.save(user);

        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            SignUpRequest usernameTaken = new SignUpRequest("yeet", "yeet", "yeet@email.com");
            authController.signUp(usernameTaken);
        });

        SignUpRequest created = new SignUpRequest("yeet2", "yeet2", "yeet2@email.com");
        assertEquals(HttpStatus.CREATED, authController.signUp(created).getStatusCode());

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.CONFLICT, thrown2.getStatus())
        );
    }

    @Test
    @DisplayName("test logout")
    public void testLogout() {
        User user = (new SignUpRequest("yeet", encoder.encode("yeet"), "yeet@yeet.com")).createUser();
        userRepository.save(user);

        String token = authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken();

        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            authController.logout(((String) null));
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            authController.logout("yeetyeet");
        });

        assertEquals(HttpStatus.OK, authController.logout(token).getStatusCode());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.BAD_REQUEST, thrown2.getStatus())
        );
    }

    @Test
    @DisplayName("test authService bearer add remove")
    public void testTokenAddRemoveBearer() {
        assertEquals("Bearer:yeet", AuthService.addBearer("yeet"));

        assertEquals("yeet", AuthService.removeBearer("Bearer:yeet"));
    }

    @Test
    @DisplayName("test authService createTokenForUser")
    public void testCreateTokenForUser() {
        
        User user = (new SignUpRequest("yeet", encoder.encode("yeet"), "yeet@yeet.com")).createUser();
        userRepository.save(user);

        // test updating token
        authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken();
        String newToken = authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken();
        
        //ser litt rart ut å kalle getToken().getToken()... ¯\_(ツ)_/¯
        assertEquals(userRepository.findByUsername("yeet").get().getToken().getToken(), newToken);

        assertThrows(IllegalArgumentException.class, () -> {
            authService.createTokenForUser(Long.valueOf(999999));
        });
        
        authService.createTokenForUser(user.getId());
    }

    @Test
    @DisplayName("test authService getUserFromToken")
    public void testGetUserFromToken() {
        User user = (new SignUpRequest("yeet", encoder.encode("yeet"), "yeet@yeet.com")).createUser();
        userRepository.save(user);

        // test updating token
        String token = authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken();

        assertThrows(IllegalArgumentException.class, () -> {
            authService.getUserFromToken(null);
        });

        assertEquals(userRepository.findByUsername(user.getUsername()).get().getId(), authService.getUserIdFromToken(token));

        assertThrows(IllegalArgumentException.class, () -> {
            authService.removeToken(null);
        });
    }

}
