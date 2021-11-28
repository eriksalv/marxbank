package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import marxbank.util.AccountType;

public class AccountTest {

  private User user;

  @BeforeEach
  public void beforeEach() throws IOException {
    user = new User((long) 1, "username", "email@email.com", "password");
  }

  @Test
  @DisplayName("test constructor")
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new SavingsAccount((long) 1, user, -5.0);
    });

    Account a = new SavingsAccount((long) 1, user, 5.0);
    assertEquals(this.user.getAccountById(1l), a);
  }

  @Test
  @DisplayName("test deposit withdraw addInterest")
  public void testDepositWithdraw() {
    Account a = new SavingsAccount((long) 1, user, 5.0);

    assertThrows(IllegalArgumentException.class, () -> {
      a.deposit(-500.0);
    });

    assertAll(() -> assertThrows(IllegalArgumentException.class, () -> {
      a.withdraw(-50.0);
    }), () -> assertThrows(IllegalStateException.class, () -> {
      a.withdraw(500.0);
    }));

    a.deposit(5000);
    assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());

    a.withdraw(500);
    assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());

    a.addInterest();
    assertEquals(4500 + 4500 * (a.getInterestRate() / 100), a.getBalance());
    assertEquals(this.user.getAccounts().get(0).getBalance(), a.getBalance());
  }

  @Test
  @DisplayName("test transactions")
  public void testTransactions() {
    Account a = new SavingsAccount((long) 1, user, 5.0);
    Account a2 = new SavingsAccount((long) 2, user, 5.0);
    a.deposit(5000);
    Transaction t = new Transaction((long) 1, a, a2, 500, true);

    assertAll(() -> assertThrows(IllegalStateException.class, () -> {
      a.addTransaction(t);
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      a.addTransaction(null);
    }));

    assertEquals(4500, a.getBalance());
    assertEquals(500, a2.getBalance());
  }

  @Test
  @DisplayName("test setters and equals")
  public void testSetters() {
    Account a = new SavingsAccount((long) 1, user, 5.0);

    // test setName and usernameValidator
    assertAll(() -> assertThrows(IllegalArgumentException.class, () -> {
      a.setName(null);
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      a.setName("ye");
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      a.setName(
          "suuuuuuuuuuuuuuuuuuuuuuuupeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeerloooooooooooooooooooooooooooooongNaaaaaaaaaaaaameeeeeeeeee");
    }), () -> assertThrows(IllegalArgumentException.class, () -> {
      a.setName(" ve ry spac y n a m e");
    }));

    a.setName("yeet");
    assertTrue(a.getName().equals("yeet"));

    // test setType and validateType
    assertThrows(IllegalArgumentException.class, () -> {
      a.setType(null);
    });
    a.setType(AccountType.CHECKING);
    assertEquals(AccountType.CHECKING, a.getType());

    // test setAccountNumber and validateAccountNumber
    assertThrows(IllegalArgumentException.class, () -> {
      a.setAccountNumber(-50);
    });
    a.setAccountNumber(500);
    assertEquals(500, a.getAccountNumber());

    assertThrows(IllegalArgumentException.class, () -> {
      a.setId(null);
    });

    a.setId((long) 99);
    assertTrue(a.getId().equals((long) 99));

    User u = new User("kristina", "kristina@kristina.no", "password");
    assertThrows(IllegalArgumentException.class, () -> {
      a.setUser(null);
    });

    a.setUser(u);
    assertEquals(u, a.getUser());

    assertThrows(IllegalArgumentException.class, () -> {
      a.setTransactions(null);
    });
    ArrayList<Transaction> t = new ArrayList<Transaction>();
    Account b = new SavingsAccount((long) 5, user);
    a.deposit(100.0);
    t.add(new Transaction((long) 10, a, b, 10, true));
    a.setTransactions(t);
    assertEquals(t, a.getTransactions());
  }
}
