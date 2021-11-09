package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

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

import marxbank.API.AccountRequest;
import marxbank.API.AccountResponse;
import marxbank.API.DepositWithdrawRequest;
import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.API.TransferRequest;
import marxbank.API.TransferResponse;
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
        assertEquals(HttpStatus.FORBIDDEN, accountController.createAccount(null, aRequest).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, accountController.createAccount("yeet", aRequest).getStatusCode());

        // create account
        assertEquals(HttpStatus.CREATED, accountController.createAccount(token, aRequest).getStatusCode());
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
        assertEquals(HttpStatus.FORBIDDEN, accountController.depositIntoAccount(null, dwRequest).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, accountController.depositIntoAccount("token", dwRequest).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, accountController.withdrawFromAccount(null, dwRequest).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, accountController.withdrawFromAccount("token", dwRequest).getStatusCode());

        // check invalid id
        assertEquals(HttpStatus.BAD_REQUEST, accountController.depositIntoAccount(token, new DepositWithdrawRequest(500, (long) 99999)).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, accountController.withdrawFromAccount(token, new DepositWithdrawRequest(500, (long) 99999)).getStatusCode());

        // check invalid amount
        assertEquals(HttpStatus.BAD_REQUEST, accountController.depositIntoAccount(token, new DepositWithdrawRequest(-500, id1)).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, accountController.withdrawFromAccount(token, new DepositWithdrawRequest(-500, id1)).getStatusCode());

        // check for sufficient funds in account
        assertEquals(HttpStatus.BAD_REQUEST, accountController.withdrawFromAccount(token, dwRequest).getStatusCode());

        // check if user doesnt own account
        assertEquals(HttpStatus.FORBIDDEN, accountController.depositIntoAccount(secondUser, dwRequest).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, accountController.withdrawFromAccount(secondUser, dwRequest).getStatusCode());

        ResponseEntity<AccountResponse> response1 = accountController.depositIntoAccount(token, dwRequest);
        ResponseEntity<AccountResponse> response2 = accountController.withdrawFromAccount(token, dwRequest);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(500, response1.getBody().getBalance());
        assertEquals(0, response2.getBody().getBalance());
    }

    @Test
    @DisplayName("Test transfer")
    public void testTransfers() {
        Long id1 = accountController.createAccount(token, new AccountRequest(AccountType.SAVING.getTypeString(), "yeet")).getBody().getId();
        Long id2 = accountController.createAccount(token, new AccountRequest(AccountType.CHECKING.getTypeString(), "yote")).getBody().getId();
        
        authController.signUp(new SignUpRequest("username", "password", "email@email.com"));
        String secondUser = authController.login(new LogInRequest("username", "password")).getBody().getToken();
        
        // test token
        assertEquals(HttpStatus.FORBIDDEN, accountController.transferBetweenAccounts(null, null).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, accountController.transferBetweenAccounts("token", null).getStatusCode());
        
        // test not owner of from account
        assertEquals(HttpStatus.FORBIDDEN, accountController.transferBetweenAccounts(secondUser, new TransferRequest(500, id1, id2)).getStatusCode());

        // test invalid accounts
        assertEquals(HttpStatus.BAD_REQUEST, accountController.transferBetweenAccounts(token, new TransferRequest(500, (long) 99999, id2)).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, accountController.transferBetweenAccounts(token, new TransferRequest(500, id1, (long) 9999)).getStatusCode());
        
        // test invalid amount
        assertEquals(HttpStatus.BAD_REQUEST, accountController.transferBetweenAccounts(token, new TransferRequest(-500, id1, id2)).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, accountController.transferBetweenAccounts(token, new TransferRequest(500, id1, id2)).getStatusCode());
        
        accountController.depositIntoAccount(token, new DepositWithdrawRequest(500, id1));

        ResponseEntity<TransferResponse> response = accountController.transferBetweenAccounts(token, new TransferRequest(250, id1, id2));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(250, response.getBody().getAmount());
    }

    @Test
    @DisplayName("Test get myAccounts")
    public void testGetMyAccounts() {
        // test token
        assertEquals(HttpStatus.FORBIDDEN, accountController.findByUser(null).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, accountController.findByUser("token").getStatusCode());

        assertEquals(0, accountController.findByUser(token).getBody().size());
        accountController.createAccount(token, new AccountRequest(AccountType.CHECKING.getTypeString(), "name"));
        assertEquals(1, accountController.findByUser(token).getBody().size());
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
