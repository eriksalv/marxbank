package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenTest {
    
    private User user;
    private String token;

    @BeforeEach
    public void setup() {
        user = new User("username", "email@email.com", "password");
        token = UUID.randomUUID().toString();
    }

    @Test
    @DisplayName("testing constructors")
    public void testingConstructors() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                Token t = new Token(null, token);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                Token t = new Token(user, null);
            })
        );
            
        Token t = new Token(user, token);
        assertEquals(user, t.getUser());
        assertEquals(token, t.getToken());
    }

    @Test
    @DisplayName("test setters")
    public void testSetters() {
        Token t = new Token();
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                t.setId(null);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                t.setToken(null);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                t.setToken("");  
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                t.setToken("             ");  
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                t.setUser(null);  
            })
        );

        t.setId((long) 5);
        t.setToken(token);
        t.setUser(user);
        assertAll(
            () -> assertEquals(5, t.getId()),
            () -> assertEquals(token, t.getToken()),
            () -> assertEquals(user, t.getUser())
        );
    }

    @Test
    @DisplayName("test equals and hashcode")
    public void testEqualAndHashcode() {
        Token t = new Token(user, token);
        t.setId((long) 5);
        assertTrue(t.equals(t));
        assertFalse(t.equals(user));
        Token t2 = new Token(user, UUID.randomUUID().toString());
        t2.setId((long) 10);
        assertFalse(t.equals(t2));

        assertEquals(Objects.hash((long) 5), t.hashCode());
    }

}
