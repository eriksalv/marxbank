package it1901.model;

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

import it1901.Bank;
import it1901.DataManager;
import it1901.model.Account;
import it1901.model.CheckingAccount;
import it1901.model.MarxAccount;

public class MarxAccountTest {
    
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
        user = new User("id", "username", "email@email.com", "password");
    }

    @Test
    @DisplayName("test constructor")
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MarxAccount("id", user, -5.0);
        });

        Account a = new MarxAccount(user, "name");
        assertEquals(this.dm.getAccounts().get(0), a);
        assertEquals(this.user.getAccounts().get(0), a);
        assertEquals(0, a.getBalance());
        assertEquals(MarxAccount.DEFAULT_INTEREST, a.getInterestRate());
    }

    @Test
    @DisplayName("test deposit")
    public void testDeposit() {
        //Case 1: account a is the only registered account
        Account a = new MarxAccount(user, "name");
        double maxBalance = MarxAccount.MAX_BALANCE;
        a.deposit(maxBalance);
        assertEquals(maxBalance, a.getBalance());
        a.deposit(10); //deposited 510 total
        assertEquals(maxBalance+10, a.getBalance());

        //Case 2: added another marxAccount
        Account a2 = new MarxAccount(user, "name2");
        a.deposit(10); //deposited 520 total
        assertEquals(maxBalance+20, a.getBalance());
        assertEquals(0, a2.getBalance());

        //Case 3: added another account, that isnt a marxAccount
        Account a3 = new CheckingAccount(user, "name3");
        a.deposit(10); //deposited 530 total
        assertEquals(maxBalance, a.getBalance());
        assertEquals(30, a3.getBalance());
        assertEquals(0, a2.getBalance());
    }

    public void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }
}
