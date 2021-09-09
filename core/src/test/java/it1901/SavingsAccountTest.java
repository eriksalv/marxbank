package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SavingsAccountTest {
    
    SavingsAccount account;

    @BeforeEach
    public void setup() {
        account = new SavingsAccount(5.0);
    }

    @Test
    @DisplayName("test deposit")
    public void testDeposit() {
        account.deposit(50.0);
        assertEquals(50.0, account.getBalance());

        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
    }

    @Test
    @DisplayName("test withdraw")
    public void testWithdraw() {

        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-25.0);
        });

        account.deposit(50.0);
        account.withdraw(25.0);
        assertEquals(25.0, account.getBalance());

        assertThrows(IllegalStateException.class, () -> {
            account.withdraw(100.0);
        });
    }

    @Test
    @DisplayName("test add interest")
    public void testAddInterest() {
        account.deposit(50.0);
        account.addInterest();

        double amount = 50 + 50.0 * (5.0/100);

        assertEquals(amount, account.getBalance());
    }

}
