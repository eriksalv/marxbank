package marxbank.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marxbank.API.AccountRequest;
import marxbank.API.TransferRequest;
import marxbank.API.TransferResponse;
import marxbank.model.Account;
import marxbank.model.CheckingAccount;
import marxbank.model.CreditAccount;
import marxbank.model.MarxAccount;
import marxbank.model.SavingsAccount;
import marxbank.model.Transaction;
import marxbank.model.User;
import marxbank.repository.AccountRepository;
import marxbank.repository.TransactionRepository;
import marxbank.repository.UserRepository;
import marxbank.util.AccountType;

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

    @Transactional
    public ArrayList<Account> getAccountsForUser(Long userId) {
        ArrayList<Account> data = new ArrayList<Account>();
        accountRepository.findByUser_Id(userId).map(e -> data.add(e));
        return data;
    }

    @Transactional
    public Account createAccount(AccountRequest request, Long userId) {
        Account a = null;

        if (!userRepository.findById(userId).isPresent()) return null;

        User user = userRepository.findById(userId).get();

        if (request.getAccountType() == AccountType.SAVING) a = new SavingsAccount(user, request.getName());
        else if (request.getAccountType() == AccountType.CHECKING) a = new CheckingAccount(user, request.getName());
        else if (request.getAccountType() == AccountType.MARX) a = new MarxAccount(user, request.getName());
        else if (request.getAccountType() == AccountType.CREDIT) a = new CreditAccount(user, request.getName());

        return a;
    }

    @Transactional
    public TransferResponse transferFunds(TransferRequest request) {
        Account toAccount = this.accountRepository.findById(request.getTo()).get();
        Account fromAccount = this.accountRepository.findById(request.getFrom()).get();

        Transaction t = new Transaction(fromAccount, toAccount, request.getAmount());

        this.accountRepository.save(toAccount);
        this.accountRepository.save(fromAccount);
        this.transactionRepository.save(t);

        return new TransferResponse(fromAccount.getId(), fromAccount.getBalance());
    }

}
