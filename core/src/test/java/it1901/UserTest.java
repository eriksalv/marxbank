package it1901;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;
    
    @BeforeEach
    public void setup() {
        user = new User();
    }

    @AfterEach
    public void cleanUp() {
        File f = new File("../data/users/99999.testUsername.json");
        if(f.exists()) {
          f.delete();  
        }
    }

    @Test
    @DisplayName("test setAccounts")
    public void testSetAccounts() {
        ArrayList<Account> testAccounts = new ArrayList<Account>(3);
        testAccounts.add(new SavingsAccount("1", user, 5.0));
        testAccounts.add(new SavingsAccount("2", user, 0.0));
        testAccounts.add(new SavingsAccount("3", user, 2.0));

        user.setAccounts(testAccounts);

        assertArrayEquals(testAccounts.toArray(), user.getAccounts().toArray());
    }

    @Test
    @DisplayName("test addAccount")
    public void testAddAccount() {
        Account t = new SavingsAccount("99", user, 5.0);
        user.addAccount(t);
        assertArrayEquals(new Account[] {t}, user.getAccounts().toArray());

        assertThrows(IllegalArgumentException.class, () -> {
            user.addAccount(t);
        });
    }

    @Test
    @DisplayName("test removeAccount")
    public void testRemoveAccount() {
        ArrayList<Account> testAccounts = new ArrayList<Account>(3);
        testAccounts.add(new SavingsAccount("1",user, 5.0));
        testAccounts.add(new SavingsAccount("2", user, 0.0));
        testAccounts.add(new SavingsAccount("3", user, 2.0));

        user.setAccounts(testAccounts);

        user.removeAccount(testAccounts.get(0));

        testAccounts.remove(0);

        assertArrayEquals(testAccounts.toArray(), user.getAccounts().toArray());

        assertThrows(IllegalArgumentException.class, () -> {
            user.removeAccount(new SavingsAccount("99", user, 8.0));
        });
    }

    @Test
    @DisplayName("test saveUser")
    public void testSaveUser() {
        File file = new File("../data/users/99999.testUsername.json");

        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                User.saveUser(null);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                User.saveUser(user);
            })
        );

        assertThrows(IllegalStateException.class, () -> {
            user.saveUser();
        });

        user.setId("99999");
        user.setUsername("testUsername");
        user.setEmail("test@testemail.com");
        user.setPassword("testpassword");

        // test save user non static
        user.saveUser();
        assertTrue(Files.exists(file.toPath()), "File should exist");

        // cleanup
        cleanUp();

        // test save user static
        User.saveUser(user);
        assertTrue(Files.exists(file.toPath()), "File should exist");
    }

    @Test
    @DisplayName("test readUser")
    public void testReadUser() throws FileNotFoundException {

        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                User.readUser("not?a?valid?path");
            }),
            () -> assertThrows(FileNotFoundException.class, () -> {
                User.readUser("../data/users/doesnt.exists.json");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                user.readUserToUser("not?a?valid?path");
            }),
            () -> assertThrows(FileNotFoundException.class, () -> {
                user.readUserToUser("../data/users.doesnt.exists.json");
            })
        );

        user.setId("99999");
        user.setUsername("testUsername");
        user.setEmail("test@testemail.com");
        user.setPassword("testpassword");

        user.saveUser();
        User t = User.readUser("../data/users/99999.testUsername.json");
        assertAll(
            () -> assertEquals(user.getId(), t.getId()),
            () -> assertEquals(user.getUsername(), t.getUsername()),
            () -> assertEquals(user.getEmail(), t.getEmail()),
            () -> assertEquals(user.getPassword(), t.getPassword())
        );
        
        User r = new User();
        r.readUserToUser("../data/users/99999.testUsername.json");
        assertAll(
            () -> assertEquals(user.getId(), r.getId()),
            () -> assertEquals(user.getUsername(), r.getUsername()),
            () -> assertEquals(user.getEmail(), r.getEmail()),
            () -> assertEquals(user.getPassword(), r.getPassword())
        );
    }
}
