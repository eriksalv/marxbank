package marxbank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marxbank.endpoint.TransactionController;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.repository.AccountRepository;
import marxbank.repository.TransactionRepository;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public ArrayList<Transaction> getTransactionForUser(Long userId) {

        List<Long> accountsId = this.accountRepository.findByUser_Id(userId).get().stream().map(Account::getId).collect(Collectors.toList());
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        accountsId.stream().forEach(a -> {
            transactions.addAll(this.transactionRepository.findTransactionByFrom_id(a).get());
            transactions.addAll(this.transactionRepository.findTransactionByReciever_id(a).get());
        });
        
        return transactions;
    }

}
