package marxbank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import marxbank.model.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findById(Long id);
    Optional<Token> findByToken(String token);
}
