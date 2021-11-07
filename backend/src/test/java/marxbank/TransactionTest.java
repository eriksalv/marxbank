package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
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

import marxbank.API.AccountRequest;
import marxbank.API.DepositWithdrawRequest;
import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.API.TransactionResponse;
import marxbank.API.TransferRequest;
import marxbank.endpoint.AccountController;
import marxbank.endpoint.AuthController;
import marxbank.endpoint.TransactionController;
import marxbank.service.TransactionService;
import marxbank.util.AccountType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionTest {
    
    @Autowired
    private TransactionController transactionController;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AuthController authController;

    @Autowired
    private TransactionService transactionService;

    private String token;
    private Long accountId1;
    private Long accountId2;

    @BeforeEach
    public void setup() {
        SignUpRequest request = new SignUpRequest("yeet", "yeet", "yeet@yeet.com");
        authController.signUp(request);
        request = new SignUpRequest("username", "password", "email@email.com");
        authController.signUp(request);
        LogInRequest lRequest = new LogInRequest("yeet", "yeet");
        token = authController.login(lRequest).getBody().getToken();
        accountId1 = accountController.createAccount(token, new AccountRequest("SAVING", "yeet")).getBody().getId();
        accountId2 = accountController.createAccount(token, new AccountRequest("CHECKING", "yote")).getBody().getId();
        accountController.depositIntoAccount(token, new DepositWithdrawRequest(500, accountId1));
    }

    @Test
    @DisplayName("test my transactions")
    public void testMyTransactions() {
        // check token
        assertEquals(HttpStatus.FORBIDDEN, transactionController.findAllTransactionForUser(null).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, transactionController.findAllTransactionForUser("yeet").getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, transactionController.findByAccount_Id(accountId1, null).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, transactionController.findByAccount_Id(accountId1, "yeet").getStatusCode());

        // check if account exists
        assertEquals(HttpStatus.NOT_FOUND, transactionController.findByAccount_Id((long) 999, token).getStatusCode());

        // check if user is trying ton get account that is not theirs
        String newUserToken = authController.login(new LogInRequest("username", "password")).getBody().getToken();
        Long newAccountId = accountController.createAccount(newUserToken, new AccountRequest("SAVING", "name")).getBody().getId();
        assertEquals(HttpStatus.NOT_FOUND, transactionController.findByAccount_Id(newAccountId, token).getStatusCode());
        
        // check my transactions
        accountController.transferBetweenAccounts(token, new TransferRequest(250, accountId1, accountId2));
        
        ResponseEntity<ArrayList<TransactionResponse>> reponse2 = transactionController.findByAccount_Id(accountId1, token);
        assertEquals(HttpStatus.OK, reponse2.getStatusCode());
        assertEquals(1, reponse2.getBody().size());
        ResponseEntity<List<TransactionResponse>> repsonse = transactionController.findAllTransactionForUser(token);

        assertEquals(HttpStatus.OK, repsonse.getStatusCode());
        assertEquals(2, repsonse.getBody().size());

        
    }

    @AfterEach
    public void cleanUp() {
        authController.logout(token);
    }

}
