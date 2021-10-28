package marxbank.repository;

import java.util.Optional;

import marxbank.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{
    
    Optional<Transaction> findById(Long id);
    Optional<Transaction> findByFrom_Id(Long id);
    Optional<Transaction> findByReciever_Id(Long id);
}
