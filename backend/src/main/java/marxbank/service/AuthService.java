package marxbank.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marxbank.model.Token;
import marxbank.model.User;
import marxbank.repository.TokenRepository;
import marxbank.repository.UserRepository;

/**
 * using the Bearer Authentication system.
 * This lets a user that is logged in be connected to a token
 * <p> see here for more:  https://swagger.io/docs/specification/authentication/bearer-authentication/
 */
@Service
public class AuthService {
    
    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private static final String BEARER_HEADER = "Bearer:";

    @Autowired
    public AuthService(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();

        Token t = user.getToken();

        if (t != null) {
            t.setToken(token);
        } else {
            user.setToken(new Token(user, token));
        }

        return token;
    }

    @Transactional
    public String createTokenForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        return createTokenForUser(user);
    }

    public User getUserFromToken(String token) {
        if (token == null) throw new IllegalArgumentException();
        token = removeBearer(token);
        if (!userRepository.findByToken_Token(token).isPresent()) return null;
        return userRepository.findByToken_Token(token).get();
    }

    public Long getUserIdFromToken(String token) {
        return getUserFromToken(token).getId();
    }

    @Transactional
    public void removeToken(String token) {
        if (token == null) throw new IllegalArgumentException();
        token = removeBearer(token);
        Token t = tokenRepository.findByToken(token).orElseThrow(IllegalArgumentException::new);
        t.getUser().setToken(null);
        tokenRepository.delete(t);
    }

    public static String addBearer(String token) {
        return String.format("%s%s", BEARER_HEADER, token);
    }


    private static String removeBearer(String token) {
        return token.replace(BEARER_HEADER, "");
    }

}
