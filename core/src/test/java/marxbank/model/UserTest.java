package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class UserTest {

  private User user;

  @Test
  @DisplayName("test constructor")
  public void testConstructor() {
    assertAll(() -> assertThrows(IllegalArgumentException.class, () -> {
      new User((long) 1, " notvalidusername", "email@test.com", "test password");
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      new User((long) 1, "user name", "email@email.com", "password");
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      new User((long) 1, "username ", "email@email.com", "password");
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      new User((long) 1, "us", "email@email.com", "password");
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      new User((long) 1,
          "usernameiswaaaaaaaayyyyytooooooooooooooooooooooooooooooooooooooolooooooooooooooooooooooooooooooooong",
          "email@email.com", "password");
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      new User((long) 1, "username", ".email@emai..com", "password");
    }));

    user = new User((long) 1, "username", "email@email.com", "password");
  }

  @Test
  @DisplayName("test account management")
  public void testAccountManagement() {
    user = new User((long) 1, "username", "email@email.com", "password");
    Account a = new SavingsAccount((long) 1, user, 5.0);
    assertEquals(user.getAccounts().get(0), a);

    assertThrows(IllegalArgumentException.class, () -> {
      user.addAccount(a);
    });

    user.removeAccount(a);
    assertEquals(0, user.getAccounts().size());

    assertThrows(IllegalArgumentException.class, () -> {
      user.removeAccount(a);
    });
  }

  @Test
  @DisplayName("test setters and stuff")
  public void testSetters() {
    user = new User((long) 1, "username", "email@email.com", "password");
    assertEquals(Objects.hash(user.getId()), user.hashCode());

    user.setEmail("newEmail@email.com");
    assertEquals(user.getEmail(), "newEmail@email.com");

    user.setUsername("newUsername");
    assertTrue(user.getUsername().equals("newUsername"));

    user.setPassword("newPassword");
    assertTrue(user.getPassword().equals("newPassword"));

    ArrayList<Account> a = new ArrayList<Account>();

    a.add(new SavingsAccount((long) 1, user));

    user.setAccounts(a);
    assertEquals(a, user.getAccounts());

    assertThrows(IllegalArgumentException.class, () -> {
      user.setId(null);
    });

    user.setId((long) 1);
    assertTrue(user.getId().equals((long) 1));

    assertFalse(user.equals(new User((long) 69, "username", "email@email.com", "password")));
  }
}
