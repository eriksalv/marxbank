package marxbank.repository;

import org.springframework.data.repository.CrudRepository;

import marxbank.model.User;

public interface UserRepository extends CrudRepository<User, String>{

    

}
