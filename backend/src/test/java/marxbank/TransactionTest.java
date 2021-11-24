package marxbank;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.web.server.ResponseStatusException;

import marxbank.API.AccountRequest;
import marxbank.API.DepositWithdrawRequest;
import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.API.TransactionRequest;
import marxbank.API.TransactionResponse;
import marxbank.endpoint.AccountController;
import marxbank.endpoint.AuthController;
import marxbank.endpoint.TransactionController;
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
        accountId1 = accountController.createAccount(token, new AccountRequest(AccountType.SAVING.getTypeString(), "yeet")).getBody().getId();
        accountId2 = accountController.createAccount(token, new AccountRequest(AccountType.CHECKING.getTypeString(), "yote")).getBody().getId();
        accountController.depositIntoAccount(token, new DepositWithdrawRequest(500, accountId1));
    }

    @Test
    @DisplayName("test my transactions")
    public void testMyTransactions() {
        // check token
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findAllTransactionForUser(null);
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findAllTransactionForUser("yeet");
        });
        ResponseStatusException thrown3 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByAccount_Id(accountId1, null);
        });
        ResponseStatusException thrown4 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByAccount_Id(accountId1, "yeet");
        });

        // check if account exists
        ResponseStatusException thrown5 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByAccount_Id((long) 999, token);
        });

        // check if user is trying to get account that is not theirs
        String newUserToken = authController.login(new LogInRequest("username", "password")).getBody().getToken();
        Long newAccountId = accountController.createAccount(newUserToken, new AccountRequest(AccountType.SAVING.getTypeString(), "name")).getBody().getId();

        ResponseStatusException thrown6 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByAccount_Id(newAccountId, token);
        });
        
        // check my transactions
        transactionController.transferBetweenAccounts(token, new TransactionRequest(accountId1, accountId2, 250));
        
        ResponseEntity<ArrayList<TransactionResponse>> reponse2 = transactionController.findByAccount_Id(accountId1, token);
        assertEquals(HttpStatus.OK, reponse2.getStatusCode());
        assertEquals(1, reponse2.getBody().size());

        ResponseEntity<List<TransactionResponse>> repsonse = transactionController.findAllTransactionForUser(token);
        assertEquals(HttpStatus.OK, repsonse.getStatusCode());
        assertEquals(1, repsonse.getBody().size());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown2.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown3.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown4.getStatus()),
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown5.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown6.getStatus())
        );        
    }

    @Test
    @DisplayName("Test transfer")
    public void testTransfers() {
        accountId1 = accountController.createAccount(token, new AccountRequest(AccountType.SAVING.getTypeString(), "yeet")).getBody().getId();
        accountId2 = accountController.createAccount(token, new AccountRequest(AccountType.CHECKING.getTypeString(), "yote")).getBody().getId();
        
        authController.signUp(new SignUpRequest("username2", "password", "email@email.com"));
        String secondUser = authController.login(new LogInRequest("username2", "password")).getBody().getToken();
        
        // test token
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.transferBetweenAccounts(null, null);
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.transferBetweenAccounts("token", null);
        });
        
        // test not owner of from account
        ResponseStatusException thrown3 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.transferBetweenAccounts(secondUser, new TransactionRequest(accountId1, accountId2, 500));
        });

        // test invalid accounts
        ResponseStatusException thrown4 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.transferBetweenAccounts(token, new TransactionRequest((long) 99999, accountId2, 500));
        });
        ResponseStatusException thrown5 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.transferBetweenAccounts(token, new TransactionRequest(accountId1, (long) 9999, 500));
        });
        
        // test invalid amount
        ResponseStatusException thrown6 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.transferBetweenAccounts(token, new TransactionRequest(accountId1, accountId2, -500));
        });
        ResponseStatusException thrown7 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.transferBetweenAccounts(token, new TransactionRequest(accountId2, accountId1, 500));
        });
        
        accountController.depositIntoAccount(token, new DepositWithdrawRequest(500, accountId1));

        ResponseEntity<TransactionResponse> response = transactionController.transferBetweenAccounts(token, new TransactionRequest(accountId1, accountId2, 250));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(250, response.getBody().getAmount());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown2.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown3.getStatus()),
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown4.getStatus()),
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown5.getStatus()),
            () -> assertEquals(HttpStatus.BAD_REQUEST, thrown6.getStatus()),
            () -> assertEquals(HttpStatus.BAD_REQUEST, thrown7.getStatus())
        ); 
    }

    @Test
    @DisplayName("test find by reciever")
    public void testFindByReciever() {
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByReciever_Id(accountId1, null);
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByReciever_Id(accountId1, "token");
        });

        ResponseStatusException thrown3 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByReciever_Id((long) 999, token);
        });

        String newToken = authController.login(new LogInRequest("username", "password")).getBody().getToken();

        ResponseStatusException thrown4 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByReciever_Id(accountId1, newToken);
        });

        transactionController.transferBetweenAccounts(token, new TransactionRequest(accountId1, accountId2, 250));

        ResponseEntity<ArrayList<TransactionResponse>> response = transactionController.findByReciever_Id(accountId2, token);

        assertEquals(1, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown2.getStatus()),
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown3.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown4.getStatus())
        ); 
    }

    @Test
    @DisplayName("test find by from")
    public void testFindByFrom() {
        ResponseStatusException thrown1 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByFrom_Id(accountId1, null);
        });
        ResponseStatusException thrown2 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByFrom_Id(accountId1, "token");
        });

        ResponseStatusException thrown3 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByFrom_Id((long) 999, token);
        });

        String newToken = authController.login(new LogInRequest("username", "password")).getBody().getToken();

        ResponseStatusException thrown4 = assertThrows(ResponseStatusException.class, () -> {
            transactionController.findByFrom_Id(accountId1, newToken);
        });

        transactionController.transferBetweenAccounts(token, new TransactionRequest(accountId1, accountId2, 250));

        ResponseEntity<ArrayList<TransactionResponse>> response = transactionController.findByFrom_Id(accountId1, token);

        assertEquals(1, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown1.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown2.getStatus()),
            () -> assertEquals(HttpStatus.NOT_FOUND, thrown3.getStatus()),
            () -> assertEquals(HttpStatus.UNAUTHORIZED, thrown4.getStatus())
        ); 
    }

    @AfterEach
    public void cleanUp() {
        authController.logout(token);
    }

}
