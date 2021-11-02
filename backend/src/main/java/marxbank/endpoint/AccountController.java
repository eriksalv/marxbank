package marxbank.endpoint;

import marxbank.API.AccountRequest;
import marxbank.API.AccountResponse;
import marxbank.API.TransferRequest;
import marxbank.API.TransferResponse;
import marxbank.model.Account;
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

import java.util.List;
import marxbank.repository.AccountRepository;
import marxbank.service.AccountService;
import marxbank.service.AuthService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AuthService authService;

    @Autowired
    public AccountController(AccountRepository accountRepository, AccountService accountService, AuthService authService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.authService = authService;
    }

    @GetMapping
    @Transactional
    public List<AccountResponse> findAll() {
        List<AccountResponse> result = new ArrayList<AccountResponse>();
        accountRepository.findAll().forEach(a -> result.add(new AccountResponse(a)));
        return result;
    }

    @GetMapping("/{id}")
    @Transactional
    public AccountResponse findById(@PathVariable Long id) throws Exception {
        return new AccountResponse(accountRepository.findById(id).orElseThrow(Exception::new));
    }

    @GetMapping("/myAccounts")
    @Transactional
    public ResponseEntity<ArrayList<AccountResponse>> findByUser(@RequestHeader(name = "Authorization", required = true) @Nullable String token) {

        User user = authService.getUserFromToken(token);

        if (user == null) ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        ArrayList<AccountResponse> accounts = new ArrayList<AccountResponse>();
    
        accountService.getAccountsForUser(user.getId()).forEach(e -> accounts.add(new AccountResponse(e)));

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @PostMapping("/createAccount")
    @Transactional
    public ResponseEntity<AccountResponse> createAccount(@RequestHeader(name = "Authorization", required = true) @Nullable String token, @RequestBody AccountRequest request) {
        User user = authService.getUserFromToken(token);

        if (user == null) ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        Account a = accountService.createAccount(request, user.getId());

        if (a == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        accountRepository.save((Account) a);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(a));
    }

    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<TransferResponse> transferBetweenAccounts(@RequestHeader(name = "Authorization", required = false) @Nullable String token, @RequestBody TransferRequest request) {
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        User user = this.authService.getUserFromToken(token);

        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (!this.accountRepository.findById(request.getFrom()).isPresent() 
            || !this.accountRepository.findById(request.getTo()).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        
        if (request.getAmount() <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (this.accountRepository.findById(request.getTo()).get().getBalance() - request.getAmount() < 0) 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        TransferResponse t = this.accountService.transferFunds(request);

        return ResponseEntity.status(HttpStatus.OK).body(t);
    }
}
