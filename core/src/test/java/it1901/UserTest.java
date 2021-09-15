package it1901;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
        ArrayList<IAccount> testAccounts = new ArrayList<IAccount>(3);
        testAccounts.add(new SavingsAccount(5.0));
        testAccounts.add(new SavingsAccount(0.0));
        testAccounts.add(new SavingsAccount(2.0));

        user.setAccounts(testAccounts);

        assertArrayEquals(testAccounts.toArray(), user.getAccounts().toArray());
    }

    @Test
    @DisplayName("test addAccount")
    public void testAddAccount() {
        IAccount t = new SavingsAccount(5.0);
        user.addAccount(t);
        assertArrayEquals(new IAccount[] {t}, user.getAccounts().toArray());

        assertThrows(IllegalArgumentException.class, () -> {
            user.addAccount(t);
        });
    }

    @Test
    @DisplayName("test removeAccount")
    public void testRemoveAccount() {
        ArrayList<IAccount> testAccounts = new ArrayList<IAccount>(3);
        testAccounts.add(new SavingsAccount(5.0));
        testAccounts.add(new SavingsAccount(0.0));
        testAccounts.add(new SavingsAccount(2.0));

        user.setAccounts(testAccounts);

        user.removeAccount(testAccounts.get(0));

        testAccounts.remove(0);

        assertArrayEquals(testAccounts.toArray(), user.getAccounts().toArray());

        assertThrows(IllegalArgumentException.class, () -> {
            user.removeAccount(new SavingsAccount(8.0));
        });
    }

    @Test
    @DisplayName("test saveUser")
    public void testSaveUser(@TempDir Path tempDir) throws Exception {
        File file = new File("../data/users/99999.testUsername.json");

        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                User.saveUser(null);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                User.saveUser(user);
            })
        );

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
