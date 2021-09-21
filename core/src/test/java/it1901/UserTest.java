package it1901;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class UserTest {

    private User user;
    private DataManager dm;

    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void beforeEachSetup() throws IOException {
        String path = tempDir.toFile().getCanonicalPath();
        dm = new DataManager(path);
    }

    @Test
    @DisplayName("test constructor")
    public void testConstructor() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("test", " notvalidusername", "email@test.com", "test password", this.dm);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "user name", "email@email.com", "password", this.dm);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "username ", "email@email.com", "password", this.dm);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "us", "email@email.com", "password", this.dm);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "usernameiswaaaaaaaayyyyytooooooooooooooooooooooooooooooooooooooolooooooooooooooooooooooooooooooooong", "email@email.com", "password", this.dm);
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                new User("id", "username", ".email@emai..com", "password", this.dm);
            })
        );

        user = new User("id", "username", "email@email.com", "password", this.dm);
        assertEquals(this.dm.getUsers().get(0), user);
        
    }

    @Test
    @DisplayName("test account management")
    public void testAccountManagement() {
        user = new User("id", "username", "email@email.com", "password", this.dm);
        Account a = new SavingsAccount("id", user, 5.0, this.dm);
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

    
}
