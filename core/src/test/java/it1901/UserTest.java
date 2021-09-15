package it1901;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;
    
    @BeforeEach
    public void setup() {
        user = new User();
    }

    @Test
    @DisplayName("test setAccounts")
    public void testSetAccounts() {
        ArrayList<Account> testAccounts = new ArrayList<Account>(3);
        testAccounts.add(new SavingsAccount(5.0));
        testAccounts.add(new SavingsAccount(0.0));
        testAccounts.add(new SavingsAccount(2.0));

        user.setAccounts(testAccounts);

        assertArrayEquals(testAccounts.toArray(), user.getAccounts().toArray());
    }

    @Test
    @DisplayName("test addAccount")
    public void testAddAccount() {
        Account t = new SavingsAccount(5.0);
        user.addAccount(t);
        assertArrayEquals(new Account[] {t}, user.getAccounts().toArray());
    }

    @Test
    @DisplayName("test removeAccount")
    public void testRemoveAccount() {
        ArrayList<Account> testAccounts = new ArrayList<Account>(3);
        testAccounts.add(new SavingsAccount(5.0));
        testAccounts.add(new SavingsAccount(0.0));
        testAccounts.add(new SavingsAccount(2.0));

        user.setAccounts(testAccounts);

        user.removeAccount(testAccounts.get(0));

        testAccounts.remove(0);

        assertArrayEquals(testAccounts.toArray(), user.getAccounts().toArray());
    }
}
