package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import marxbank.Bank;
import marxbank.model.Account;
import marxbank.model.CreditAccount;
import marxbank.model.User;

public class CreditAccountTest {

    private User user;

    @BeforeEach
    public void beforeEach() throws IOException {
        resetSingleton();
        user = new User("id", "username", "email@email.com", "password");
    }

    @Test
    @DisplayName("test contructor")
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            Account a = new CreditAccount("id", user, -5.0, "navn", 69000);
        });

        String name = "kredittkonto";

        CreditAccount a = new CreditAccount(user, name);
        assertEquals(this.user.getAccounts().get(0), a);
        assertEquals(200, a.getCreditLimit());
    }

    @Test
    @DisplayName("test withdraw")
    public void testWithdraw() {
        CreditAccount a = new CreditAccount(user, "name");
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
