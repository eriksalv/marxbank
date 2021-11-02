package marxbank.repository;

import java.util.List;
import java.util.Optional;

import marxbank.model.Transaction;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{
    
    Optional<List<Transaction>> findTransactionByFrom_id(Long accountId);
    Optional<List<Transaction>> findTransactionByReciever_id(Long account);
<<<<<<< HEAD

=======
>>>>>>> origin/master
}
