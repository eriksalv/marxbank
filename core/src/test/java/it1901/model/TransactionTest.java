package it1901.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private Account a1;
    private Account a2;

    private Transaction transaction;    

    @BeforeEach
    public void setup() throws IOException {
        a1 = new SavingsAccount("id1", new User("id", "username", "email@email.com", "password"), 2);
        a2 = new SavingsAccount("id2", new User("id2", "username2", "email@gmail.com", "password2"), 3);
        a1.deposit(100);
        a2.deposit(100);
    }

    
    @Test
    @DisplayName("test commitTransaction with a1 and a2 as param")
    public void testCommitTransaction1() throws IOException {
        transaction = new Transaction("t", a1, a2, 50, true);
        
        assertEquals(transaction.getFrom(), a1);
        assertEquals(transaction.getReciever(), a2);
        assertEquals(transaction.getAmount(), 50);
        
        assertEquals(a1.getBalance(), 50);
        assertEquals(a2.getBalance(), 150);
        assertEquals(a1.getTransactions().size(), 1);
        assertEquals(a2.getTransactions().size(), 1);

        Transaction t2 = new Transaction("t2", a1, a2, 50.0, false);

        assertEquals(t2.getFrom(), a1);
        assertEquals(t2.getReciever(), a2);
        assertEquals(t2.getAmount(), 50);
        
        assertEquals(a1.getBalance(), 50);
        assertEquals(a2.getBalance(), 150);
        assertEquals(a1.getTransactions().size(), 2);
        assertEquals(a2.getTransactions().size(), 2);

    }

    @Test
    @DisplayName("test commitTransaction with a1 and null as param")
    public void testCommitTransaction2() throws IOException {
        assertThrows(IllegalStateException.class, () -> {
            transaction = new Transaction("t", a1, null, 50, true);
        });

        assertEquals(a1.getBalance(), 100);
        assertEquals(a1.getTransactions().size(), 0);

    }

    @Test
    @DisplayName("test equals")
    public void testEquals() {
        transaction = new Transaction("t", a1, a2, 50, true);

        assertTrue(transaction.equals(transaction));
        assertFalse(transaction.equals("yeet"));
        assertFalse(transaction.equals(new Transaction("t2", a1, a2, 5, false)));
    }
}

