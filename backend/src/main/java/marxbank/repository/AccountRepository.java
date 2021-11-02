package marxbank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import marxbank.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
    
    Optional<Account> findById(Long id);
<<<<<<< HEAD
    Optional<Account> findByUser_Id(Long userId);
=======
    Optional<List<Account>> findByUser_Id(Long userId);
>>>>>>> 615650796fc73f08736098744eaccb0fdc5ee257
}
