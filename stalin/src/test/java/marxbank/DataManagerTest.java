package marxbank;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import marxbank.Bank;
import marxbank.model.Account;
import marxbank.model.SavingsAccount;
import marxbank.model.Transaction;
import marxbank.model.User;

public class DataManagerTest {
    
    private User user;
    private Account account;
    private Account account2;
    private Transaction transaction;

    @TempDir
    static Path tempDir;

    @BeforeAll
    static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void setup() {
        resetAll();
        user = DataManager.manager().createUser("yeetman", "email@email.com", "password");
        account = DataManager.manager().createAccount("Sparekonto", user, "name");
        account2 = DataManager.manager().createAccount("Sparekonto", user, "name2");
        account.deposit(500.0);
        transaction = DataManager.manager().createTransaction(account, account2, 50.0);
    }

    @Test
    @DisplayName("Test Create User, Account and Transaction, Adders and deleters")
    public void testCreatorsAddersDeleters() {
        // test creators and adders
        assertTrue(DataManager.manager().getUsers().get(0).equals(user));
        assertTrue(DataManager.manager().getAccounts().get(0).equals(account));
        assertTrue(DataManager.manager().getAccounts().get(1).equals(account2));
        assertTrue(DataManager.manager().getTransactions().get(0).equals(transaction));

        // test adders throw
        assertThrows(IllegalArgumentException.class, () -> {
            DataManager.manager().addUser(user);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            DataManager.manager().addAccount(account);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            DataManager.manager().addTransaction(transaction);
        });

        // test deleters
        DataManager.manager().deleteTransaction(transaction);
        DataManager.manager().deleteAccount(account);
        DataManager.manager().deleteUser(user);

        assertTrue(DataManager.manager().getUsers().size() == 0);
        assertTrue(DataManager.manager().getAccounts().size() == 1);
        assertTrue(DataManager.manager().getTransactions().size() == 0);

        assertThrows(IllegalArgumentException.class, () -> {
            DataManager.manager().deleteUser(user);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            DataManager.manager().deleteAccount(account);
        });
   
        assertThrows(IllegalArgumentException.class, () -> {
            DataManager.manager().deleteTransaction(transaction);
        });
    }

    @Test
    @DisplayName("test different checkers")
    public void testDifferenctCheckers() {
        assertTrue(DataManager.manager().checkIfUserExists(user.getId()));
        assertTrue(DataManager.manager().checkIfAccountExists(account));
        assertTrue(DataManager.manager().checkIfAccountExists(account2.getId()));
        assertTrue(DataManager.manager().checkIfTransactionExists(transaction));
        assertTrue(DataManager.manager().checkIfTransactionExists(transaction.getId()));

        assertFalse(DataManager.manager().checkIfUserExists("yeeters"));
        assertFalse(DataManager.manager().checkIfAccountExists("yeeters"));
        assertFalse(DataManager.manager().checkIfAccountExists(new SavingsAccount(user, "yeet")));
        assertFalse(DataManager.manager().checkIfTransactionExists("yeet"));
        assertFalse(DataManager.manager().checkIfTransactionExists(new Transaction("id", account, account2, 50.0, false)));
        
    }

    @Test
    @DisplayName("Test differnet getters")
    public void testDifferentGetters() {
        assertTrue(DataManager.manager().getUserByUsername("yeetman").equals(user));
        assertTrue(DataManager.manager().getUser(user.getId()).equals(user));
        assertNull(DataManager.manager().getUser("nopeman"));
        assertTrue(DataManager.manager().getAccount(account.getId()).equals(account));
        assertNull(DataManager.manager().getAccount("nopeaccount"));
        assertTrue(DataManager.manager().getTransaction(transaction.getId()).equals(transaction));
        assertNull(DataManager.manager().getTransaction("nopetransaction"));
    }
    
    @Test
    @DisplayName("Test save and parse")
    public void testSaveAndParse() throws IOException {
        //DataManager.manager().setPath(tempDir.resolve("data").toFile().getCanonicalPath());
        DataManager.manager().setPath("../data");
        DataManager.manager().save();
        assertTrue(tempDir.resolve("data").toFile().exists());

        DataManager.manager().resetData();
        DataManager.manager().parse();
        // assertTrue(DataManager.manager().getUsers().get(0).equals(user));
        // assertTrue(DataManager.manager().getAccounts().size() == 2);
        // assertTrue(DataManager.manager().getAccount(account.getId()).equals(account));
        // assertTrue(DataManager.manager().getTransactions().get(0).equals(transaction));
    }

    private void resetAll() {
        Bank.getInstanceBank().clearAccounts();
        DataManager.manager().resetData();
    }


}
