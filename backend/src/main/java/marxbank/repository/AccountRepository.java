package marxbank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import marxbank.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
    
    Optional<Account> findById(Long id);
    Optional<Account> findByUser_Id(Long userId);
}
