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
    user = DataManager.createUser("yeetman", "email@email.com", "password");
    account = DataManager.createAccount("Sparekonto", user, "name");
    account2 = DataManager.createAccount("Sparekonto", user, "name2");
    account.deposit(500.0);
    transaction = DataManager.createTransaction(account, account2, 50.0);
  }

  @Test
  @DisplayName("Test Create User, Account and Transaction, Adders and deleters")
  public void testCreatorsAddersDeleters() {
    // test creators and adders

    System.out.println(DataManager.getAccounts().contains(account));

    assertTrue(DataManager.getUsers().get(0).equals(user));
    assertTrue(DataManager.getAccounts().get(0).equals(account));
    assertTrue(DataManager.getAccounts().get(1).equals(account2));
    assertTrue(DataManager.getTransactions().get(0).equals(transaction));

    // test adders throw
    assertThrows(IllegalArgumentException.class, () -> {
      DataManager.addUser(user);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      DataManager.addAccount(account);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      DataManager.addTransaction(transaction);
    });

    // test deleters
    DataManager.deleteTransaction(transaction);
    DataManager.deleteAccount(account);
    DataManager.deleteUser(user);

    assertTrue(DataManager.getUsers().size() == 0);
    assertTrue(DataManager.getAccounts().size() == 1);
    assertTrue(DataManager.getTransactions().size() == 0);

    assertThrows(IllegalArgumentException.class, () -> {
      DataManager.deleteUser(user);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      DataManager.deleteAccount(account);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      DataManager.deleteTransaction(transaction);
    });
  }

  @Test
  @DisplayName("test different checkers")
  public void testDifferenctCheckers() {
    assertTrue(DataManager.checkIfUserExists(user.getId()));
    assertTrue(DataManager.checkIfAccountExists(account));
    assertTrue(DataManager.checkIfAccountExists(account2.getId()));
    assertTrue(DataManager.checkIfTransactionExists(transaction));
    assertTrue(DataManager.checkIfTransactionExists(transaction.getId()));

    assertFalse(DataManager.checkIfUserExists(99999));
    assertFalse(DataManager.checkIfAccountExists(99999));
    assertFalse(DataManager.checkIfAccountExists(new SavingsAccount(user, "yeet")));
    assertFalse(DataManager.checkIfTransactionExists(999999999));
    assertFalse(DataManager.
        checkIfTransactionExists(new Transaction((long) 1, account, account2, 50.0, false)));

  }

  @Test
  @DisplayName("Test differnet getters")
  public void testDifferentGetters() {
    assertTrue(DataManager.getUserByUsername("yeetman").equals(user));
    assertTrue(DataManager.getUser(user.getId()).equals(user));
    assertNull(DataManager.getUser((long) -50.0));
    assertTrue(DataManager.getAccount(account.getId()).equals(account));
    assertNull(DataManager.getAccount((long) -50.0));
    assertTrue(DataManager.getTransaction(transaction.getId()).equals(transaction));
    assertNull(DataManager.getTransaction((long) -50.0));
  }

  @Test
  @DisplayName("Test save and parse")
  public void testSaveAndParse() throws IOException {
    DataManager.setPath(tempDir.resolve("data").toFile().getCanonicalPath());
    DataManager.save();
    assertTrue(tempDir.resolve("data").toFile().exists());

    DataManager.resetData();
    DataManager.parse();
    assertTrue(DataManager.getUsers().get(0).equals(user));
    assertTrue(DataManager.getAccounts().size() == 2);
    assertTrue(DataManager.getAccount(account.getId()).getId().equals(account.getId()));
    assertTrue(DataManager.getTransactions().get(0).equals(transaction));
  }

  private void resetAll() {
    DataManager.resetData();
  }


}
