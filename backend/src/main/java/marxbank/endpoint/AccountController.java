package marxbank.endpoint;

import marxbank.API.AccountResponse;
import marxbank.model.Account;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import marxbank.repository.AccountRepository;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    
    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
}
