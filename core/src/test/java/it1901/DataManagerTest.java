package it1901;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DataManagerTest {
    
    private DataManager dm;

    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void cleanUpBetween() {
        try {
            tempDir.resolve("data/users.json").toFile().delete();
            tempDir.resolve("data/accounts.json").toFile().delete();
            tempDir.resolve("data/transactions.json").toFile().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() throws IOException {
        Files.createFile(tempDir.resolve("data/test.test"));

        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> {
                this.dm = new DataManager("not?a?valid?path");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                this.dm = new DataManager("this/dir/does/not/exist");
            }),
            () -> assertThrows(IllegalArgumentException.class, () -> {
                this.dm = new DataManager(tempDir.resolve("data/test.test").toFile().getCanonicalPath());
            })
        );
    }


    //@Test
    @DisplayName("test password has been taken")
    public void testPassword() throws Exception {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());

        this.dm.parse();
        assertThrows(IllegalArgumentException.class, () -> {
            User user1 = new User("username", "email@email.com", "samePassword", dm);
            User user2 = new User("username2", "email@gmail.com", "samePassword", dm);
            this.dm.save();
        });
    }

    @Test
    @DisplayName("test parse and Save")
    public void testParseAndSave() throws Exception {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());

        this.dm.parse();
        assertAll(
            () -> assertTrue(Files.exists(tempDir.resolve("data/users.json"))),
            () -> assertTrue(Files.exists(tempDir.resolve("data/accounts.json"))),
            () -> assertTrue(Files.exists(tempDir.resolve("data/transactions.json")))
        );

        User user = new User("uniqueId", "testUsername", "test@email.com", "password", this.dm);
        Account a = new SavingsAccount("test1", user, 5.0, this.dm);
        a.deposit(5000.0);
        Account a2 = new SavingsAccount("test2", user, 10.0, this.dm);
        Transaction t = new Transaction("test", a, a2, 500, this.dm, true);

        this.dm.save();

        assertEquals(String.format("{\"users\":%s}", new ObjectMapper().writeValueAsString(this.dm.getUsers())), Files.readString(tempDir.resolve("data/users.json")));
        assertEquals(String.format("{\"accounts\":%s}", new ObjectMapper().writeValueAsString(this.dm.getAccounts())), Files.readString(tempDir.resolve("data/accounts.json")));
        assertEquals(String.format("{\"transactions\":%s}", new ObjectMapper().writeValueAsString(this.dm.getTransactions())), Files.readString(tempDir.resolve("data/transactions.json")));


        DataManager dm2 = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        dm2.parse();

        assertEquals(this.dm.getUsers(), dm2.getUsers());
        assertEquals(this.dm.getAccounts(), dm2.getAccounts());
        assertEquals(this.dm.getTransactions(), dm2.getTransactions());

        System.out.println(dm2.getUsers());
        dm2.getAccounts().forEach(e -> System.out.println(e));
        dm2.getUsers().get(0).getAccounts().forEach(e -> System.out.println(e));
        dm2.getUsers().get(0).getAccounts().forEach(e -> e.getTransactions().forEach(o -> System.out.println(o)));
    }

    @Test
    @DisplayName("test save of each type")
    public void testSaveOfEachType() throws Exception {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        User user = new User("uniqueId", "testUsername", "test@email.com", "password", this.dm);
        Account a = new SavingsAccount("test1", user, 5.0, this.dm);
        a.deposit(5000.0);
        Account a2 = new SavingsAccount("test2", user, 10.0, this.dm);
        Transaction t = new Transaction("test", a, a2, 500, this.dm, true);

        assertFalse(Files.exists(tempDir.resolve("data/users.json")));
        this.dm.saveUsers(tempDir.resolve("data/users.json").toFile());
        assertTrue(Files.exists(tempDir.resolve("data/users.json")));
        User u2 = new User("yeet", "testYeet", "test@yeet.com", "passyeet", this.dm);
        
        assertThrows(Exception.class, () -> {
            tempDir.resolve("data/users.json").toFile().setReadOnly();
            tempDir.resolve("data/users.json").toFile().setWritable(false);
            this.dm.saveUsers(tempDir.resolve("data/users.json").toFile());
        });

        assertFalse(Files.exists(tempDir.resolve("data/accounts.json")));
        this.dm.saveAccounts(tempDir.resolve("data/accounts.json").toFile());
        assertTrue(Files.exists(tempDir.resolve("data/accounts.json")));
        tempDir.resolve("data/accounts.json").toFile().setReadOnly();
        Account a3 = new SavingsAccount("test3", user, this.dm);

        assertThrows(Exception.class, () -> {
            this.dm.saveUsers(tempDir.resolve("data/accounts.json").toFile());
        });
        
        assertFalse(Files.exists(tempDir.resolve("data/transactions.json")));
        this.dm.saveTransactions(tempDir.resolve("data/transactions.json").toFile());
        assertTrue(Files.exists(tempDir.resolve("data/transactions.json")));
        tempDir.resolve("data/transactions.json").toFile().setReadOnly();
        Transaction t3 = new Transaction(a, a2, 5.0, this.dm);

        assertThrows(Exception.class, () -> {
            this.dm.saveUsers(tempDir.resolve("data/transactions.json").toFile());
        });


    }

    @Test
    @DisplayName("test updateUser and updateAccount and updateTransaction")
    public void testUpdaters() throws IOException {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());

        User user = new User("test", "testUsername", "test@email.com", "password", this.dm);
        user.setUsername("newUsername");
        assertEquals(user, this.dm.getUser("test"));

        Account a = new SavingsAccount("id", user, 5.0, this.dm);
        a.setInterestRate(10.0);
        assertEquals(a, this.dm.getAccount("id"));
        Account a2 = new SavingsAccount("testId", user, 8.0, this.dm);
        a2.deposit(5000);
    }

    @Test
    @DisplayName("test deleteUser, deleteAccount and deleteTransaction")
    public void testDeleters() throws Exception {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        User user = new User("test", "testUsername", "test@email.com", "password", this.dm);
        User user2 = new User("test2", "testUsername2", "test2@email.com", "password2", this.dm);

        Account deleteAccount = new SavingsAccount(user, dm, "delete");
        Account account = new SavingsAccount(user, dm, "name");
        account.deposit(500);
        Account account2 = new SavingsAccount(user, dm, "name2");


        Transaction t = new Transaction(account, account2, 50, dm);
        Transaction t1 = new Transaction(account, account2, 100, dm);

        // save user stuff
        this.dm.save();
        this.dm.deleteUser(user2);
        this.dm.deleteAccount(deleteAccount);
        this.dm.deleteTransaction(t1);
        this.dm.save();

        DataManager dm2 = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        dm2.parse();

        assertTrue(dm2.getIndexOfUser(user2) == -1);
        assertTrue(dm2.getIndexOfUser(user) == 0);

        assertTrue(dm2.getIndexOfAccount(deleteAccount) == -1);
        assertTrue(dm2.getIndexOfAccount(account) == 0);

        assertTrue(dm2.getIndexOfTransaction(t1) == -1);
        assertTrue(dm2.getIndexOfTransaction(t) == 0);

        DataManager dm3 = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        dm3.addUser(user);
        dm3.addAccount(account);
        dm3.addAccount(account2);
        dm3.addTransaction(t);

        dm3.parse();
    }

    @Test
    @DisplayName("test get User by Username")
    public void testGetUserByUsername() throws IOException {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        User user = new User("test", "testUsername", "test@email.com", "password", this.dm);

        assertEquals(user, this.dm.getUserByUsername("testUsername"));
        assertNull(this.dm.getUserByUsername("UserThatDoesNotExist"));
    }

    @Test
    @DisplayName("test getter and setters for different stuff")
    public void testLoggedInUser() throws IOException {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        User user = new User("test", "testUsername", "test@email.com", "password", this.dm);
        Account account = new SavingsAccount(user, dm, "name");
        account.deposit(500);
        Account account2 = new SavingsAccount(user, dm, "name");
        Transaction t = new Transaction(account, account2, 50, this.dm);

        this.dm.setLoggedInUser(user);
        assertEquals(user, this.dm.getLoggedInUser());

        assertEquals(user, this.dm.getUser("test"));
        assertNull(this.dm.getUser("DoesntExist"));

        assertEquals(account, this.dm.getAccount(account.getId()));
        assertNull(this.dm.getAccount("DoesNotExist"));

        assertEquals(t, this.dm.getTransaction(t.getId()));
        assertNull(this.dm.getTransaction("DoesNotExist"));
    }

    @Test
    @DisplayName("test add and delete")
    public void testAddAndDelete() throws IOException {
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        User user = new User("test", "testUsername", "test@email.com", "password", this.dm);
        assertThrows(IllegalArgumentException.class, () -> {
            User user2 = new User("test", "testUsername", "test@email.com", "password", this.dm);
        });

        Account account = new SavingsAccount("id", user, 5.0, dm, "name", 100000);
        assertThrows(IllegalArgumentException.class, () -> {
            this.dm.addAccount(account);
        });
        Account account2 = new SavingsAccount("2", user, 5.0, dm, "name", 100055);

        account.deposit(500);
        Transaction t = new Transaction(account, account2, 50, this.dm);
        assertThrows(IllegalArgumentException.class, () -> {
            this.dm.addTransaction(t);
        });

        this.dm.deleteUser(user);
        assertThrows(IllegalArgumentException.class, () -> {
            this.dm.deleteUser(user);
        });

        this.dm.deleteAccount(account);
        assertThrows(IllegalArgumentException.class, () -> {
            this.dm.deleteAccount(account);
        });

        this.dm.deleteTransaction(t);
        assertThrows(IllegalArgumentException.class, () -> {
            this.dm.deleteTransaction(t);
        });
    }

    @Test
    @DisplayName("test different checks")
    public void testDifferentChecks() throws IOException{
        this.dm = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        User user = new User("test", "testUsername", "test@email.com", "password", this.dm);

        assertTrue(this.dm.checkIfUsernameIsTaken(user.getUsername()));
        assertFalse(this.dm.checkIfUsernameIsTaken("AVeryFancyUsernameThatIsNotTaken"));
        
        assertTrue(this.dm.checkIfUserExists("test"));
        assertFalse(this.dm.checkIfUserExists("userDoesNotExist"));

        Account account = new SavingsAccount(user, dm, "name");
        assertTrue(this.dm.checkIfAccountExists(account));
        assertTrue(this.dm.checkIfAccountExists(account.getId()));
        this.dm.deleteAccount(account);
        assertFalse(this.dm.checkIfAccountExists(account));
        assertFalse(this.dm.checkIfAccountExists("AccountDoesNotExist"));

        Account account2 = new SavingsAccount(user, dm, "name");
        Account account3 = new SavingsAccount(user, dm, "name");
        account2.deposit(500);
        Transaction t = new Transaction(account2, account3, 50, this.dm);
        assertTrue(this.dm.checkIfTransactionExists(t));
        assertTrue(this.dm.checkIfTransactionExists(t.getId()));
        this.dm.deleteTransaction(t);
        assertFalse(this.dm.checkIfTransactionExists(t));
        assertFalse(this.dm.checkIfTransactionExists(t.getId()));

        assertTrue(this.dm.checkIfUserExists(user));
        this.dm.deleteUser(user);
        assertFalse(this.dm.checkIfUserExists(user));


    }

}
