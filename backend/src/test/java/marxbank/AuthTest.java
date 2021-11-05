package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.endpoint.AuthController;
import marxbank.model.User;
import marxbank.repository.TokenRepository;
import marxbank.repository.UserRepository;
import marxbank.service.AuthService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private AuthController authController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("test login post")
    public void testLoginPost() {
        // too short username/password
        LogInRequest badRequest = new LogInRequest("u", "bbbbbbbb");
        assertEquals(HttpStatus.BAD_REQUEST, authController.login(badRequest).getStatusCode());
        badRequest = new LogInRequest("bbbbbbbb", "u");
        assertEquals(HttpStatus.BAD_REQUEST, authController.login(badRequest).getStatusCode());

        // user doesn't exist
        LogInRequest notFoundRequest = new LogInRequest("username", "password");
        assertEquals(HttpStatus.NOT_FOUND, authController.login(notFoundRequest).getStatusCode());

        // create user
        User user = (new SignUpRequest("yeet", "yeet", "yeet@yeet.com")).createUser();
        userRepository.save(user);

        // wrong password
        LogInRequest wrongPassword = new LogInRequest("yeet", "password");
        assertEquals(HttpStatus.FORBIDDEN, authController.login(wrongPassword).getStatusCode());

        // correct password
        LogInRequest correctRequest = new LogInRequest("yeet", "yeet");
        assertEquals(HttpStatus.OK, authController.login(correctRequest).getStatusCode());
    }

    @Test
    @DisplayName("test login get")
    public void testLoginGet() {
        assertEquals(HttpStatus.FORBIDDEN, authController.login(((String) null)).getStatusCode());
        
        String invalidToken ="Bearer:yeetyeet";

        assertEquals(HttpStatus.FORBIDDEN, authController.login(invalidToken).getStatusCode());

        User user = (new SignUpRequest("yeet", "yeet", "yeet@yeet.com")).createUser();
        userRepository.save(user);

        String token = String.format("Bearer:%s", authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken());
        assertEquals(HttpStatus.OK, authController.login(token).getStatusCode());
    }

    @Test
    @DisplayName("test signup")
    public void testSignup() {
        SignUpRequest badRequest = new SignUpRequest("ye", "ye", "yeet");
        assertEquals(HttpStatus.BAD_REQUEST, authController.signUp(badRequest).getStatusCode());

        User user = (new SignUpRequest("yeet", "yeet", "yeet@email.com")).createUser();
        userRepository.save(user);

        SignUpRequest usernameTaken = new SignUpRequest("yeet", "yeet", "yeet@email.com");
        assertEquals(HttpStatus.CONFLICT, authController.signUp(usernameTaken).getStatusCode());

        SignUpRequest created = new SignUpRequest("yeet2", "yeet2", "yeet2@email.com");
        assertEquals(HttpStatus.CREATED, authController.signUp(created).getStatusCode());
    }

    @Test
    @DisplayName("test logout")
    public void testLogout() {
        User user = (new SignUpRequest("yeet", "yeet", "yeet@yeet.com")).createUser();
        userRepository.save(user);

        String token = authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken();

        assertEquals(HttpStatus.FORBIDDEN, authController.logout(((String) null)).getStatusCode());

        assertEquals(HttpStatus.BAD_REQUEST, authController.logout("yeetyeet").getStatusCode());

        assertEquals(HttpStatus.OK, authController.logout(token).getStatusCode());
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
        
        User user = (new SignUpRequest("yeet", "yeet", "yeet@yeet.com")).createUser();
        userRepository.save(user);

        // test updating token
        String oldtoken = authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken();
        String token = authController.login(new LogInRequest("yeet", "yeet")).getBody().getToken();

        assertEquals(userRepository.findByUsername("yeet").get().getToken().getToken(), token);

        assertThrows(IllegalArgumentException.class, () -> {
            authService.createTokenForUser(Long.valueOf(999999));
        });
        
        authService.createTokenForUser(user.getId());
    }

    @Test
    @DisplayName("test authService getUserFromToken")
    public void testGetUserFromToken() {
        User user = (new SignUpRequest("yeet", "yeet", "yeet@yeet.com")).createUser();
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
