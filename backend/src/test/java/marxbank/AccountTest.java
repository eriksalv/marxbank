package marxbank;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.server.ResponseStatusException;

import marxbank.API.AccountRequest;
import marxbank.API.AccountResponse;
import marxbank.API.DepositWithdrawRequest;
import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.endpoint.AccountController;
import marxbank.endpoint.AuthController;
import marxbank.service.AccountService;
import marxbank.util.AccountType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthController authController;

    private String token;

    @BeforeEach
    public void setup() {
        SignUpRequest request = new SignUpRequest("yeet", "yeet", "yeet@yeet.com");
        authController.signUp(request);
        LogInRequest lRequest = new LogInRequest("yeet", "yeet");
        token = authController.login(lRequest).getBody().getToken();
    }

    @Test
    @DisplayName("Testing create account")
    public void testCreateAccount() {
        AccountRequest aRequest = new AccountRequest("Sparekonto", "yeet");

        // test invalid token
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            accountController.createAccount(null, aRequest);
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            accountController.createAccount("yeet", aRequest);
        });

        // create account
        assertEquals(HttpStatus.CREATED, accountController.createAccount(token, aRequest).getStatusCode());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown2.getStatus())
        ); 
    }

    @Test
    @DisplayName("Test deposit and withdraw")
    public void testDepositAndWithdraw() {
        AccountRequest aRequest = new AccountRequest(AccountType.SAVING.getTypeString(), "yeet");
        Long id1 = accountController.createAccount(token, aRequest).getBody().getId();

        authController.signUp(new SignUpRequest("username", "password", "email@email.com"));
        String secondUser = authController.login(new LogInRequest("username", "password")).getBody().getToken();

        DepositWithdrawRequest dwRequest = new DepositWithdrawRequest(500, id1);
        // check token
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            accountController.depositIntoAccount(null, dwRequest);
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            accountController.depositIntoAccount("token", dwRequest);
        });
        ResponseStatusException thrown3 = assertThrows(ResponseStatusException.class, () -> {
            accountController.withdrawFromAccount(null, dwRequest);
        });
        ResponseStatusException thrown4 = assertThrows(ResponseStatusException.class, () -> {
            accountController.withdrawFromAccount("token", dwRequest);
        });

        // check invalid id
        ResponseStatusException thrown5 = assertThrows(ResponseStatusException.class, () -> {
            accountController.depositIntoAccount(token, new DepositWithdrawRequest(500, (long) 99999));
        });
        ResponseStatusException thrown6 = assertThrows(ResponseStatusException.class, () -> {
            accountController.withdrawFromAccount(token, new DepositWithdrawRequest(500, (long) 99999));
        });

        // check invalid amount
        ResponseStatusException thrown7 = assertThrows(ResponseStatusException.class, () -> {
            accountController.depositIntoAccount(token, new DepositWithdrawRequest(-500, id1));
        });
        ResponseStatusException thrown8 = assertThrows(ResponseStatusException.class, () -> {
            accountController.withdrawFromAccount(token, new DepositWithdrawRequest(-500, id1));
        });

        // check for sufficient funds in account
        ResponseStatusException thrown9 = assertThrows(ResponseStatusException.class, () -> {
            accountController.withdrawFromAccount(token, dwRequest);
        });

        // check if user doesnt own account
        ResponseStatusException thrown10 = assertThrows(ResponseStatusException.class, () -> {
            accountController.depositIntoAccount(secondUser, dwRequest);
        });
        ResponseStatusException thrown11 = assertThrows(ResponseStatusException.class, () -> {
            accountController.withdrawFromAccount(secondUser, dwRequest);
        });

        ResponseEntity<AccountResponse> response1 = accountController.depositIntoAccount(token, dwRequest);
        ResponseEntity<AccountResponse> response2 = accountController.withdrawFromAccount(token, dwRequest);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(500, response1.getBody().getBalance());
        assertEquals(0, response2.getBody().getBalance());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown2.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown3.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown4.getStatus()),
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown5.getStatus()),
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown6.getStatus()),
            () -> assertEquals(HttpStatus.BAD_REQUEST, thrown7.getStatus()),
            () -> assertEquals(HttpStatus.BAD_REQUEST, thrown8.getStatus()),
            () -> assertEquals(HttpStatus.BAD_REQUEST, thrown9.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown10.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown11.getStatus())
        ); 
    }

    @Test
    @DisplayName("Test get myAccounts")
    public void testGetMyAccounts() {
        // test token
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            accountController.findByUser(null);
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            accountController.findByUser("token");
        });

        assertEquals(0, accountController.findByUser(token).getBody().size());
        accountController.createAccount(token, new AccountRequest(AccountType.CHECKING.getTypeString(), "name"));
        assertEquals(1, accountController.findByUser(token).getBody().size());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown2.getStatus())
        ); 
    }

    @Test
    @DisplayName("Test checks in accountService")
    public void testChecksInAccountService() {
        assertNull(accountService.createAccount(new AccountRequest(AccountType.CHECKING.getTypeString(), "yeet"), (long) 99999999));
        assertFalse(accountService.checkIfUserOwnsAccount((long) 9999, (long) 80085));
    }

    @AfterEach
    public void cleanUp() {
        authController.logout(token);
    }

}
