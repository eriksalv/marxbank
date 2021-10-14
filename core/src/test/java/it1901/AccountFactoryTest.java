package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it1901.model.Account;
import it1901.model.CheckingAccount;
import it1901.model.MarxAccount;
import it1901.model.SavingsAccount;
import it1901.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AccountFactoryTest {
    
    private DataManager dm;
    private User user;
    private Account account;
    private AccountFactory accf;

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
        user = new User("id", "username", "email@email.com", "password");
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
        Account a1 = AccountFactory.createFrom("BSU", "id1", user, "Ola Nordmann", 1);
        Account a2 = AccountFactory.createFrom("Sparekonto", "id2", user, "Ola Nordmann", 1);
        Account a3 = AccountFactory.createFrom("Brukskonto", "id3", user, "Ola Nordmann", 1);
        Account a4 = AccountFactory.createFrom("Marxkonto", "id4", user, "Ola Nordmann", 1);

        assertEquals(a1, null);
        assertTrue(a2 instanceof SavingsAccount);
        assertTrue(a3 instanceof CheckingAccount);
        assertTrue(a4 instanceof MarxAccount);
    }

}
