package marxbank.repository;

import java.util.Optional;

import marxbank.model.Transaction;
import marxbank.model.User;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{
    
    Optional<Transaction> findUserTransactions(User User);
}
