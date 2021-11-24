package marxbank.endpoint;

import marxbank.API.AccountRequest;
import marxbank.API.AccountResponse;
import marxbank.API.DepositWithdrawRequest;
import marxbank.API.PublicAccountResponse;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import marxbank.repository.AccountRepository;
import marxbank.service.AccountService;
import marxbank.service.AuthService;
import marxbank.service.TransactionService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final AuthService authService;

    @Autowired
    public AccountController(AccountRepository accountRepository, AccountService accountService,
            AuthService authService, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.authService = authService;
        this.transactionService = transactionService;
    }

    /**
     * finds all accounts that a given user has had transactions with.
     * @param token token of logged in user
     * @return list of accounts with HttpStatus 200
     */
    @GetMapping("/transactions")
    @Transactional
    public ResponseEntity<List<PublicAccountResponse>> findByTransactions(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token) {

        if (token == null || authService.getUserFromToken(token) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

        User user = authService.getUserFromToken(token);

        List<PublicAccountResponse> result = new ArrayList<>();

        List<Transaction> userTransactions = transactionService.getTransactionForUser(user.getId());
        List<Transaction> filtered = userTransactions.stream().filter(t -> t.isBetweenDifferentUsers()).collect(Collectors.toList());

        filtered.forEach(t -> {
            if (t.getFrom().getUser().equals(user)) {
                if (accountRepository.findById(t.getReciever().getId()).isPresent()) {
                    result.add(new PublicAccountResponse(t.getReciever()));
                }
            } else {
                if (accountRepository.findById(t.getFrom().getId()).isPresent()) {
                    result.add(new PublicAccountResponse(t.getFrom()));
                }
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    /**
     * Gets all accounts for an User.
     * @param token for user
     * @return returns list of accounts with resposne 200
     * @return a response with null body and code 401 if token is invalid
     */
    @GetMapping("/myAccounts")
    @Transactional
    public ResponseEntity<ArrayList<AccountResponse>> findByUser(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token) {
        if (token == null || authService.getUserFromToken(token) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

        User user = authService.getUserFromToken(token);

        ArrayList<AccountResponse> accounts = new ArrayList<AccountResponse>();

        accountService.getAccountsForUser(user.getId()).forEach(e -> accounts.add(new AccountResponse(e)));

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    /**
     * Get certian account of users accounts.
     * @param token for user
     * @param id of account
     * @return account data with response 200 if found
     * @return a response with null body and a status code 401 if token is invalid
     * @return a repsonse with null body and status code 404 if account is not found 
     */
    @GetMapping("/myAccounts/{id}")
    @Transactional
    public ResponseEntity<AccountResponse> findByUserAndId(
            @RequestHeader(name = "Authorization", required = true) @Nullable String token, @PathVariable Long id) {
        if (token == null || authService.getUserFromToken(token) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

        if (!accountRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");
        }
        Account acc = accountRepository.findById(id).get();

        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponse(acc));
    }

    /**
     * Creates account for user.
     * @param token for user
     * @param request for new account
     * @return a response with body of the account and a code of 201
     * @return a response with null body and code 401 if token is invalid
     * @return a resposne with null body and code 400 if request is invalid
     */
    @PostMapping("/createAccount")
    @Transactional
    public ResponseEntity<AccountResponse> createAccount(
            @RequestHeader(name = "Authorization", required = true) @Nullable String token,
            @RequestBody AccountRequest request) {
        if (token == null || authService.getUserFromToken(token) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

        User user = authService.getUserFromToken(token);

        Account a;
        try {
            a = accountService.createAccount(request, user.getId());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        a.setAccountNumber(a.generateAccountNumber());

        accountRepository.save(a);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(a));
    }

    /**
     * deposits money into account.
     * @param token for user
     * @param request deposit request with data
     * @return a response with body of the account and a code of 200
     * @return a response with null body and code 401 if token is invalid
     * @return a resposne with null body and code 400 if request is invalid
     */
    @PostMapping("/deposit")
    @Transactional
    public ResponseEntity<AccountResponse> depositIntoAccount(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token,
            @RequestBody DepositWithdrawRequest request) {
        if (token == null || authService.getUserFromToken(token) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

        User user = this.authService.getUserFromToken(token);

        if (!accountRepository.findById(request.getAccountId()).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");

        if (!accountService.checkIfUserOwnsAccount(user.getId(), request.getAccountId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        
        Account a = accountRepository.findById(request.getAccountId()).get();
        try {
            a.deposit(request.getAmount());
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        accountRepository.save(a);

        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponse(a));
    }

    /**
     * withdraws money from account.
     * @param token for user
     * @param request deposit request with data
     * @return a response with body of the account and a code of 200
     * @return a response with null body and code 401 if token is invalid
     * @return a resposne with null body and code 400 if request is invalid
     */
    @PostMapping("/withdraw")
    @Transactional
    public ResponseEntity<AccountResponse> withdrawFromAccount(
            @RequestHeader(name = "Authorization", required = false) @Nullable String token,
            @RequestBody DepositWithdrawRequest request) {
        if (token == null || authService.getUserFromToken(token) == null)
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

        User user = this.authService.getUserFromToken(token);

        if (!accountRepository.findById(request.getAccountId()).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");

        if (!accountService.checkIfUserOwnsAccount(user.getId(), request.getAccountId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

        Account a = accountRepository.findById(request.getAccountId()).get();

        try {
            a.withdraw(request.getAmount());
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        accountRepository.save(a);

        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponse(a));
    }
}
