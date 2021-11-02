package marxbank.service;

import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> 615650796fc73f08736098744eaccb0fdc5ee257

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import marxbank.model.Transaction;
=======
import marxbank.endpoint.TransactionController;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.repository.AccountRepository;
>>>>>>> 615650796fc73f08736098744eaccb0fdc5ee257
import marxbank.repository.TransactionRepository;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
<<<<<<< HEAD

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional

    public ArrayList<Transaction> getTransactionsForAccount(Long id){
        ArrayList<Transaction> data = new ArrayList<Transaction>();
        transactionRepository.findByFrom_Id(id).map(e -> data.addAll(e));
        transactionRepository.findByReciever_Id(id).map(e -> data.addAll(e));
        return data;
=======
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
>>>>>>> 615650796fc73f08736098744eaccb0fdc5ee257
    }

}
