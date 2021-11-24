package marxbank.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marxbank.API.AccountRequest;
import marxbank.model.Account;
import marxbank.model.User;
import marxbank.repository.AccountRepository;
import marxbank.repository.UserRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public ArrayList<Account> getAccountsForUser(Long userId) {
        return (ArrayList<Account>) this.accountRepository.findByUser_Id(userId).get();
    }

    @Transactional
    public Account createAccount(AccountRequest request, Long userId) {
        if (!userRepository.findById(userId).isPresent()) return null;
        
        User user = userRepository.findById(userId).get();
        
        Account a = request.buildAccount();

        a.setUser(user);

        return a;
    }
    
    public boolean checkIfUserOwnsAccount(Long userId, Long accountId) {
        if (!accountRepository.findById(accountId).isPresent()) return false;
        return accountRepository.findById(accountId).get().getUser().getId().equals(userId);
    }

}
