package marxbank.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marxbank.model.Transaction;
import marxbank.repository.TransactionRepository;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;

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
    }

}
