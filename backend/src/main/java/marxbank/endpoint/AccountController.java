package marxbank.endpoint;

import marxbank.API.AccountRequest;
import marxbank.API.AccountResponse;
import marxbank.API.DepositWithdrawRequest;
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
import java.util.Optional;

import marxbank.repository.AccountRepository;
import marxbank.service.AccountService;
import marxbank.service.AuthService;
import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

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
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> result = new ArrayList<AccountResponse>();
        accountRepository.findAll().forEach(a -> result.add(new AccountResponse(a)));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        if (!accountRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Account acc = accountRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponse(acc));
    }

    @GetMapping("/myAccounts")
    @Transactional
    public ResponseEntity<ArrayList<AccountResponse>> findByUser(@RequestHeader(name = "Authorization", required = false) @Nullable String token) {
        if (token==null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
       
        User user = authService.getUserFromToken(token);

        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        ArrayList<AccountResponse> accounts = new ArrayList<AccountResponse>();
    
        accountService.getAccountsForUser(user.getId()).forEach(e -> accounts.add(new AccountResponse(e)));

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @GetMapping("/myAccounts/{id}")
    @Transactional
    public ResponseEntity<AccountResponse> findByUserAndId(@RequestHeader(name = "Authorization", required = true) @Nullable String token, @PathVariable Long id) {
        if (token==null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
       
        User user = authService.getUserFromToken(token);

        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (!accountRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Account acc = accountRepository.findById(id).get();

        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponse(acc));
    }

    @PostMapping("/createAccount")
    @Transactional
    public ResponseEntity<AccountResponse> createAccount(@RequestHeader(name = "Authorization", required = true) @Nullable String token, @RequestBody AccountRequest request) {
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        
        User user = authService.getUserFromToken(token);

        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        Account a = accountService.createAccount(request, user.getId());

        if (a == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        a.setAccountNumber();

        accountRepository.save(a);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(a));
    }

    @PostMapping("/deposit")
    @Transactional
    public ResponseEntity<AccountResponse> depositIntoAccount(@RequestHeader(name = "Authorization", required = false) @Nullable String token, @RequestBody DepositWithdrawRequest request) {
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        User user = this.authService.getUserFromToken(token);

        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (!accountRepository.findById(request.getAccountId()).isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        
        if (!accountService.checkIfUserOwnsAccount(user.getId(), request.getAccountId())) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        
        if (request.getAmount() <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Account a = accountRepository.findById(request.getAccountId()).get();
        a.deposit(request.getAmount());

        accountRepository.save(a);

        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponse(a));
    }

    @PostMapping("/withdraw")
    @Transactional
    public ResponseEntity<AccountResponse> withdrawFromAccount(@RequestHeader(name = "Authorization", required = false) @Nullable String token, @RequestBody DepositWithdrawRequest request) {
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        User user = this.authService.getUserFromToken(token);

        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if (!accountRepository.findById(request.getAccountId()).isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        
        if (!accountService.checkIfUserOwnsAccount(user.getId(), request.getAccountId())) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            
        
        if (request.getAmount() <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Account a = accountRepository.findById(request.getAccountId()).get();

        if (a.getBalance() - request.getAmount() < 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 

        a.withdraw(request.getAmount());

        accountRepository.save(a);

        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponse(a));
    }
}
