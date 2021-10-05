package it1901;

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

public class CreditAccountTest {
    private DataManager dm;
    private User user;

    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        resetSingleton();
        String path = tempDir.toFile().getCanonicalPath();
        dm = new DataManager(path);
        user = new User("id", "username", "email@email.com", "password", dm);
    }

    @Test
    @DisplayName("test contructor")
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            Account a = new CreditAccount("id", user, -5.0, dm, "navn", 69000);
        });

        String name = "kredittkonto";

        CreditAccount a = new CreditAccount(user, dm, name);
        assertEquals(this.dm.getAccounts().get(0), a);
        assertEquals(this.user.getAccounts().get(0), a);
        assertEquals(200, a.getCreditLimit());
    }

    @Test
    @DisplayName("test withdraw")
    public void testWithdraw() {
        CreditAccount a = new CreditAccount(user, dm, "name");
        a.deposit(200);
        a.withdraw(50);
        assertEquals(150, a.getBalance());

        a.withdraw(200);
        assertEquals(-50, a.getBalance());
    }

    public void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }
}
