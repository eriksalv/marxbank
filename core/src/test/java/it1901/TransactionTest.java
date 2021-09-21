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

public class TransactionTest {

    private Account a1;
    private Account a2;

    private Transaction transaction;    

    private DataManager dm;

    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    public void setup() throws IOException {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        a1 = new SavingsAccount("id1", new User("id", "username", "email@email.com", "password", dm), 2, dm);
        a2 = new SavingsAccount("id2", new User("id2", "username2", "email@gmail.com", "password2", dm), 3, dm);
        a1.deposit(100);
        a2.deposit(100);
    }

    
    @Test
    @DisplayName("test commitTransaction with a1 and a2 as param")
    public void testCommitTransaction1() throws IOException {
        setup();
        transaction = new Transaction("t", a1, a2, 50, dm);

        assertEquals(transaction.getFrom(), a1);
        assertEquals(transaction.getReciever(), a2);
        assertEquals(transaction.getAmount(), 50);

        assertEquals(a1.getBalance(), 50);
        assertEquals(a2.getBalance(), 150);
        assertEquals(a1.getTransactions().size(), 1);
        assertEquals(a2.getTransactions().size(), 1);

    }

    @Test
    @DisplayName("test commitTransaction with a1 and null as param")
    public void testCommitTransaction2() throws IOException {
        setup();
        assertThrows(IllegalStateException.class, () -> {
            transaction = new Transaction("t", a1, null, 50, dm);;
        });

        assertEquals(a1.getBalance(), 100);
        assertEquals(a1.getTransactions().size(), 0);

    }
}

