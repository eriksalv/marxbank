package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import marxbank.model.Account;
import marxbank.model.SavingsAccount;
import marxbank.model.Transaction;
import marxbank.model.User;

public class AccountTest {

    private User user;

    @BeforeEach
    public void beforeEach() throws IOException {
        user = new User("id", "username", "email@email.com", "password");
    }

    @Test
    @DisplayName("test constructor")
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SavingsAccount("id", user, -5.0);
        });

        Account a = new SavingsAccount("id", user, 5.0);
        assertEquals(this.user.getAccounts().get(0), a);
    }

    @Test
    @DisplayName("test deposit withdraw addInterest")
    public void testDepositWithdraw() {
        Account a = new SavingsAccount("id", user, 5.0);

        assertThrows(IllegalArgumentException.class, () -> {
            a.deposit(-500.0);
        });

        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                a.withdraw(-50.0);
            }),
            () -> assertThrows(IllegalStateException.class, () -> {
                a.withdraw(500.0);
            })
        );

        a.deposit(5000);
        assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());

        a.withdraw(500);
        assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());

        a.addInterest();
        assertEquals(4500 + 4500*(a.getInterestRate()/100), a.getBalance());
        assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());
    }

    @Test
    @DisplayName("test transactions")
    public void testTransactions() {
        Account a = new SavingsAccount("id", user, 5.0);
        Account a2 = new SavingsAccount("id2", user, 5.0);
        a.deposit(5000);
        Transaction t = new Transaction("id", a, a2, 500, true);

        assertThrows(IllegalStateException.class, () -> {
            a.addTransaction(t);
        });

        assertEquals(4500, a.getBalance());
        assertEquals(500, a2.getBalance());
    }

    @Test
    @DisplayName("test setters and equals")
    public void testSetters() {
        Account a = new SavingsAccount("id", user, 5.0);

        assertThrows(IllegalArgumentException.class, () -> {
            a.setName(null);
        });

        a.setName("yeet");
        assertTrue(a.getName().equals("yeet"));

        assertFalse(a.equals(new User("username", "email@email.com", "password")));
        
        assertThrows(IllegalArgumentException.class, () -> {
            a.setId(null);
        });

        a.setId("idNooneElseHas");
        assertTrue(a.getId().equals("idNooneElseHas"));

        User u = new User("kristina", "kristina@kristina.no", "password");
        assertThrows(IllegalArgumentException.class, () -> {
            a.setUser(null);
        });

        a.setUser(u);
        assertEquals(u, a.getUser());

        ArrayList<Transaction> t = new ArrayList<Transaction>();
        Account b = new SavingsAccount("iddddd", user);
        a.deposit(100.0);
        t.add(new Transaction("id", a, b, 10, true));
        a.setTransactions(t);
        assertEquals(t, a.getTransactions());
    }
}
