package marxbank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import marxbank.model.User;

public interface UserRepository extends CrudRepository<User, String>{

    Optional<User> findByUserName(String username);

}
