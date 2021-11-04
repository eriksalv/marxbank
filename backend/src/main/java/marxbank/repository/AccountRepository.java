package marxbank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import marxbank.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
    
    Optional<Account> findById(Long id);
    Optional<List<Account>> findByUser_Id(Long userId);
}
