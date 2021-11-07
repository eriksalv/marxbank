package marxbank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marxbank.API.TransactionRequest;
import marxbank.API.TransactionResponse;
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
    public ArrayList<Transaction> getTransactionsForAccount(Long id) {
        ArrayList<Transaction> data = new ArrayList<Transaction>();
        transactionRepository.findByFrom_Id(id).map(e -> data.addAll(e));
        transactionRepository.findByReciever_Id(id).map(e -> data.addAll(e));
        return data;
    }

    @Transactional
    public ArrayList<Transaction> getTransactionForUser(Long userId) {

        List<Long> accountsId = this.accountRepository.findByUser_Id(userId).get().stream().map(Account::getId).collect(Collectors.toList());
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        accountsId.stream().forEach(a -> {
            transactions.addAll(this.transactionRepository.findByFrom_Id(a).get());
            transactions.addAll(this.transactionRepository.findByReciever_Id(a).get());
        });
        
        return transactions;
    }

    @Transactional
    public TransactionResponse resolveTransactionRequest(TransactionRequest request) {
        Account toAccount = this.accountRepository.findById(request.getTo()).get();
        Account fromAccount = this.accountRepository.findById(request.getFrom()).get();

        Transaction t = new Transaction(fromAccount, toAccount, request.getAmount());

        this.accountRepository.save(toAccount);
        this.accountRepository.save(fromAccount);
        this.transactionRepository.save(t);

        return new TransactionResponse(t);
    }

}
