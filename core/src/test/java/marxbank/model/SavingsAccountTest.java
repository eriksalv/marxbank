package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SavingsAccountTest {
    
    private User user;

    @BeforeEach
    public void setup() throws IOException {
        this.user = new User((long) 1, "username", "emai@email.com", "password");
    }

    @Test
    @DisplayName("test of first constructor")
    public void testConstructors() {
        SavingsAccount normalConstrutor = new SavingsAccount((long) 1, user, 3);
        assertAll(
            () -> assertEquals((long) 1, normalConstrutor.getId()),
            () -> assertEquals(user, normalConstrutor.getUser()),
            () ->  assertEquals(3.0, normalConstrutor.getInterestRate())
        );

        SavingsAccount normalSetRateConstructor = new SavingsAccount((long) 2, user, 5.0);
        assertAll(
            () -> assertEquals((long) 2, normalSetRateConstructor.getId()),
            () -> assertEquals(user, normalSetRateConstructor.getUser()),
            () -> assertEquals(5.0, normalSetRateConstructor.getInterestRate())
        );

        SavingsAccount noIdNameConstructor = new SavingsAccount(user, "name");
        assertAll(
            () -> assertEquals(user, noIdNameConstructor.getUser()),
            () -> assertEquals("name", noIdNameConstructor.getName())  
        );

        SavingsAccount longConstructor = new SavingsAccount((long) 3, user, 5.0, "longConstructor", 5);
        assertAll(
            () -> assertEquals((long) 3, longConstructor.getId()),
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
