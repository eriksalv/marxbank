package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import marxbank.model.Account;
import marxbank.model.CheckingAccount;
import marxbank.model.MarxAccount;
import marxbank.model.SavingsAccount;
import marxbank.model.User;

public class AccountFactoryTest {
    
    private User user;

    @BeforeEach
    public void beforeEach() throws IOException {
        user = new User((long) 1, "username", "email@email.com", "password");
    }

    @Test
    @DisplayName("test createMethod")
    public void testCreateMethod() {
        Account a1 = AccountFactory.create("BSU", user,"Ola Nordmann");
        Account a2 = AccountFactory.create("Sparekonto", user, "Ola Nordmann");
        Account a3 = AccountFactory.create("Brukskonto", user, "Ola Nordmann");
        Account a4 = AccountFactory.create("Marxkonto", user, "Ola Nordmann");

        assertEquals(a1, null);
        assertTrue(a2 instanceof SavingsAccount);
        assertTrue(a3 instanceof CheckingAccount);
        assertTrue(a4 instanceof MarxAccount);
    }

    @Test
    @DisplayName("test createFromMethod")
    public void testCreateFromMethod() {
        Account a1 = AccountFactory.createFrom("BSU", (long) 2, user, "Ola Nordmann", 1);
        Account a2 = AccountFactory.createFrom("Sparekonto", (long) 3, user, "Ola Nordmann", 1);
        Account a3 = AccountFactory.createFrom("Brukskonto", (long) 4, user, "Ola Nordmann", 1);
        Account a4 = AccountFactory.createFrom("Marxkonto", (long) 5, user, "Ola Nordmann", 1);

        assertEquals(a1, null);
        assertTrue(a2 instanceof SavingsAccount);
        assertTrue(a3 instanceof CheckingAccount);
        assertTrue(a4 instanceof MarxAccount);
    }

}
