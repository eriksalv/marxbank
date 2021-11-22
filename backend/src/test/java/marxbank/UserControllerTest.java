package marxbank;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.server.ResponseStatusException;

import marxbank.API.EditUserRequest;
import marxbank.API.LogInRequest;
import marxbank.API.LogInResponse;
import marxbank.API.SignUpRequest;
import marxbank.API.UserResponse;
import marxbank.endpoint.AuthController;
import marxbank.endpoint.UserController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    

    @Autowired
    private AuthController authController;

    @Autowired
    private UserController userController;

    private String token;

    private long userId;

    @BeforeEach
    public void setup() {
        SignUpRequest request = new SignUpRequest("yeet", "yeet", "yeet@yeet.com");
        authController.signUp(request);
        LogInRequest lRequest = new LogInRequest("yeet", "yeet");
        ResponseEntity<LogInResponse> response = authController.login(lRequest);
        token = response.getBody().getToken();
        userId = response.getBody().getUserResponse().getId();
    }

    @Test
    @DisplayName("test get user")
    public void testGetUser() {

        assertAll(
            () -> assertThrows(ResponseStatusException.class, () -> {
                userController.findById((String) null, (long) 1);
            }),
            () -> assertThrows(ResponseStatusException.class, () -> {
                userController.findById("token", (long) 1);
            }),
            () -> assertThrows(ResponseStatusException.class, () -> {
                userController.findById(token, (long) 99);
            })
        );

        assertEquals(userController.findById(token, userId).getBody().getId(), userId);
    }

    @Test
    @DisplayName("test edit user")
    public void testEditUser() {

        // token and id check
        assertAll(
            // null token
            () -> assertThrows(ResponseStatusException.class, () -> {
                EditUserRequest request = new EditUserRequest("yeet", "password", "oldPassword", "yeet@yeet.com");
                userController.editUser((String) null, (long) 1, request);
            }),
            // not valid token
            () -> assertThrows(ResponseStatusException.class, () -> {
                EditUserRequest request = new EditUserRequest("yeet", "password", "oldPassword", "yeet@yeet.com");
                userController.editUser("token", (long) 1, request);
            }),
            // not valid id
            () -> assertThrows(ResponseStatusException.class, () -> {
                EditUserRequest request = new EditUserRequest("yeet", "password", "oldPassword", "yeet@yeet.com");
                userController.editUser(token, (long) 99, request);
            })
        );

        // bad input
        assertAll(
            // wrong password old password
            () -> assertThrows(ResponseStatusException.class, () -> {
                EditUserRequest request = new EditUserRequest("yeet", "password", "oldPassword", "yeet@yeet.com");
                userController.editUser(token, userId, request);
            }),
            // username taken
            () -> assertThrows(ResponseStatusException.class, () -> {
                authController.signUp(new SignUpRequest("username", "password", "email@email.com"));
                EditUserRequest request = new EditUserRequest("username", "password", "yeet", "yeet@yeet.com");
                userController.editUser(token, userId, request);
            }),
            // bad email
            () -> assertThrows(ResponseStatusException.class, () -> {
                EditUserRequest request = new EditUserRequest("yeet", "password", "yeet", "emailbad");
                userController.editUser(token, userId, request);
            })
        );


        // valid edit
        EditUserRequest request = new EditUserRequest("yeet", "password", "yeet", "new@email.com");
        ResponseEntity<UserResponse> response = userController.editUser(token, userId, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody().getId());
        assertEquals("new@email.com", response.getBody().getEmail());
    }

}
