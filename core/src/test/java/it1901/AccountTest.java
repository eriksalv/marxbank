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

public class AccountTest {
    
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
        String path = tempDir.toFile().getCanonicalPath();
        dm = new DataManager(path);
        user = new User("id", "username", "email@email.com", "password", dm);
    }

    @Test
    @DisplayName("test constructor")
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SavingsAccount("id", user, -5.0, dm);
        });

        Account a = new SavingsAccount("id", user, 5.0, dm);
        assertEquals(this.dm.getAccounts().get(0), a);
        assertEquals(this.user.getAccounts().get(0), a);
    }

    @Test
    @DisplayName("test deposit withdraw addInterest")
    public void testDepositWithdraw() {
        Account a = new SavingsAccount("id", user, 5.0, dm);

        a.deposit(5000);
        assertEquals(this.dm.getAccounts().get(0).getBalance(), a.getBalance());
        assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());

        a.withdraw(500);
        assertEquals(this.dm.getAccounts().get(0).getBalance(), a.getBalance());
        assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());

        a.addInterest();
        assertEquals(4500 + 4500*(a.getInterestRate()/100), a.getBalance());
        assertEquals(this.dm.getAccounts().get(0).getBalance(), a.getBalance());
        assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());
    }

    @Test
    @DisplayName("test transactions")
    public void testTransactions() {
        Account a = new SavingsAccount("id", user, 5.0, dm);
        Account a2 = new SavingsAccount("id2", user, 5.0, dm);
        a.deposit(5000);
        Transaction t = new Transaction("id", a, a2, 500, dm, true);

        assertThrows(IllegalStateException.class, () -> {
            a.addTransaction(t);
        });

        assertEquals(4500, a.getBalance());
        assertEquals(500, a2.getBalance());
    }

}
