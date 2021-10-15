package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import marxbank.model.Account;
import marxbank.model.SavingsAccount;
import marxbank.model.User;


public class UserTest {

    private User user;

    @Test
    @DisplayName("test constructor")
    public void testConstructor() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("test", " notvalidusername", "email@test.com", "test password");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "user name", "email@email.com", "password");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "username ", "email@email.com", "password");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "us", "email@email.com", "password");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "usernameiswaaaaaaaayyyyytooooooooooooooooooooooooooooooooooooooolooooooooooooooooooooooooooooooooong", "email@email.com", "password");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "username", ".email@emai..com", "password");
            })
        );

        user = new User("id", "username", "email@email.com", "password");
    }

    @Test
    @DisplayName("test account management")
    public void testAccountManagement() {
        user = new User("id", "username", "email@email.com", "password");
        Account a = new SavingsAccount("id", user, 5.0);
        assertEquals(user.getAccounts().get(0), a);
        
        assertThrows(IllegalArgumentException.class, () -> {
            user.addAccount(a);
        });

        user.removeAccount(a);
        assertEquals(0, user.getAccounts().size());

        assertThrows(IllegalArgumentException.class, () -> {
            user.removeAccount(a);
        });
    }

    @Test
    @DisplayName("test setters and stuff")
    public void testSetters() {
        user = new User("id", "username", "email@email.com", "password");
        assertEquals(Objects.hash(user.getId()), user.hashCode());

        user.setEmail("newEmail@email.com");
        assertTrue(user.getEmail().equals("newEmail@email.com"));

        user.setUsername("newUsername");
        assertTrue(user.getUsername().equals("newUsername"));

        user.setPassword("newPassword");
        assertTrue(user.getPassword().equals("newPassword"));

        ArrayList<Account> a = new ArrayList<Account>();

        a.add(new SavingsAccount("id", user));

        user.setAccounts(a);
        assertEquals(a, user.getAccounts());
        
        assertThrows(IllegalArgumentException.class, () -> {
            user.setId(null);
        });

        user.setId("newId");
        assertTrue(user.getId().equals("newId"));

        assertFalse(user.equals(new SavingsAccount("a", user)));

        assertFalse(user.equals(new User("nope", "username", "email@email.com", "password")));
    }

    
}
