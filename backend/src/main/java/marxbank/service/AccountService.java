package marxbank.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marxbank.API.AccountRequest;
import marxbank.API.TransferRequest;
import marxbank.API.TransferResponse;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;
import marxbank.repository.AccountRepository;
import marxbank.repository.TransactionRepository;
import marxbank.repository.UserRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
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
    
    //burde ikke dette være i transaction service?
    @Transactional
    public TransferResponse transferFunds(TransferRequest request) {
        Account toAccount = this.accountRepository.findById(request.getTo()).get();
        Account fromAccount = this.accountRepository.findById(request.getFrom()).get();

        Transaction t = new Transaction();
        t.setFrom(fromAccount);
        t.setReciever(toAccount);
        t.setAmount(request.getAmount());
        t.commitTransaction();

        this.accountRepository.save(toAccount);
        this.accountRepository.save(fromAccount);
        this.transactionRepository.save(t);

        return new TransferResponse(fromAccount.getId(), fromAccount.getBalance());
    }
    
    public boolean checkIfUserOwnsAccount(Long userId, Long accountId) {
        if (!accountRepository.findById(accountId).isPresent()) return false;
        return accountRepository.findById(accountId).get().getUser().getId() == userId;
    }

}
