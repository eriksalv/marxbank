package it1901.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import it1901.DataManager;
import it1901.model.SavingsAccount;

public class SavingsAccountTest {
    
    @TempDir
    static Path tempDir;
    private DataManager dm;
    private User user;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void setup() throws IOException {
        this.dm = new DataManager(tempDir.toFile().getCanonicalPath());
        this.user = new User("id", "username", "emai@email.com", "password");
    }

    @Test
    @DisplayName("test of first constructor")
    public void testConstructors() {
        SavingsAccount normalConstrutor = new SavingsAccount("1", user, 3);
        assertAll(
            () -> assertEquals("1", normalConstrutor.getId()),
            () -> assertEquals(user, normalConstrutor.getUser()),
            () ->  assertEquals(3.0, normalConstrutor.getInterestRate())
        );

        SavingsAccount normalSetRateConstructor = new SavingsAccount("2", user, 5.0);
        assertAll(
            () -> assertEquals("2", normalSetRateConstructor.getId()),
            () -> assertEquals(user, normalSetRateConstructor.getUser()),
            () -> assertEquals(5.0, normalSetRateConstructor.getInterestRate())
        );

        SavingsAccount noIdNameConstructor = new SavingsAccount(user, "name");
        assertAll(
            () -> assertEquals(user, noIdNameConstructor.getUser()),
            () -> assertEquals("name", noIdNameConstructor.getName())  
        );

        SavingsAccount longConstructor = new SavingsAccount("3", user, 5.0, "longConstructor", 5);
        assertAll(
            () -> assertEquals("3", longConstructor.getId()),
            () -> assertEquals(user, longConstructor.getUser()),
            () -> assertEquals(5.0, longConstructor.getInterestRate()),
            () -> assertEquals("longConstructor", longConstructor.getName()),
            () -> assertEquals(5, longConstructor.getAccountNumber())
        );   
    }

    @Test
    @DisplayName("test get account type")
    public void testGetAccountType() {
        SavingsAccount a = new SavingsAccount(user, "name");
        assertEquals("Sparekonto", a.getAccountType());
    }

    

}
