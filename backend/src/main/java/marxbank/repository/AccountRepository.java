package marxbank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import marxbank.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
    
    Optional<Account> findByAccount(Long account);
    Optional<Account> findByUserID(Long UserId);
}
