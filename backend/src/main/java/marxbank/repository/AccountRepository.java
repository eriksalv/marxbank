package marxbank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import marxbank.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
    
    Optional<Account> findById(Long id);
    Optional<Account> findByUser(Long UserId);
}
