package it1901;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class SavingsAccountTest {
    
    @TempDir
    private static Path tempDir;
    private DataManager dm;
    private User user;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void setup() throws IOException {
        this.dm = new DataManager(tempDir.toFile().getCanonicalPath());
        this.user = new User("id", "username", "emai@email.com", "password", dm);
    }

    @Test
    @DisplayName("test of first constructor")
    public void testFirstConstructor() {
        SavingsAccount a = new SavingsAccount("id", user, 3, dm);
        
    }

    

}
