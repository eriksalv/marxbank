package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private Transaction transaction;    

    Account a1 = new SavingsAccount(new User(), 2);
    Account a2 = new SavingsAccount(new User(), 3);

    @BeforeEach
    public void setup() {
        a1.deposit(100);
        a2.deposit(100);
    }

    
    @Test
    @DisplayName("test commitTransaction with a1 and a2 as param")
    public void testCommitTransaction1() {
        transaction = new Transaction(a1, a2, 50);

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
    public void testCommitTransaction2() {
        assertThrows(IllegalStateException.class, () -> {
            transaction = new Transaction(a1, null, 50);;
        });

        assertEquals(a1.getBalance(), 100);
        assertEquals(a1.getTransactions().size(), 0);

    }
}

