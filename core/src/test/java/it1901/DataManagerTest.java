package it1901;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
        Transaction t = new Transaction("test", a, a2, 500, this.dm);

        this.dm.save();

        assertEquals(String.format("{\"users\":%s}", new ObjectMapper().writeValueAsString(this.dm.getUsers())), Files.readString(tempDir.resolve("data/users.json")));
        assertEquals(String.format("{\"accounts\":%s}", new ObjectMapper().writeValueAsString(this.dm.getAccounts())), Files.readString(tempDir.resolve("data/accounts.json")));
        assertEquals(String.format("{\"transactions\":%s}", new ObjectMapper().writeValueAsString(this.dm.getTransactions())), Files.readString(tempDir.resolve("data/transactions.json")));


        DataManager dm2 = new DataManager(tempDir.resolve("data").toFile().getCanonicalPath());
        dm2.parse();
        assertEquals(this.dm.getUsers(), dm2.getUsers());
        assertEquals(this.dm.getAccounts(), dm2.getAccounts());
        assertEquals(this.dm.getTransactions(), dm2.getTransactions());
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

        Transaction t = new Transaction("id", a2, a, 500, this.dm);
        //t.setAmount(12345);
        //assertEquals(t, this.dm.getTransaction("id"));
        
    }

}
