package marxbank.repository;

import java.util.List;
import java.util.Optional;

import marxbank.model.Transaction;
<<<<<<< HEAD
=======

>>>>>>> 615650796fc73f08736098744eaccb0fdc5ee257
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{
    
<<<<<<< HEAD
    Optional<Transaction> findById(Long id);
    Optional <List<Transaction>> findByFrom_Id(Long id);
    Optional <List<Transaction>> findByReciever_Id(Long id);
    
=======
    Optional<List<Transaction>> findTransactionByFrom_id(Long accountId);
    Optional<List<Transaction>> findTransactionByReciever_id(Long account);
>>>>>>> 615650796fc73f08736098744eaccb0fdc5ee257
}
