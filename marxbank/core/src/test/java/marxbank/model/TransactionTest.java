package marxbank.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class TransactionTest {

  private Account a1;
  private Account a2;

  private Transaction transaction;

  @BeforeEach
  public void setup() throws IOException {
    a1 = new SavingsAccount((long) 1, new User((long) 1, "username", "email@email.com", "password"),
        2);
    a2 = new SavingsAccount((long) 2,
        new User((long) 2, "username2", "email@gmail.com", "password2"), 3);
    a1.deposit(100);
    a2.deposit(100);
  }


  @Test
  public void testConstructor() {
    Transaction t1 = new Transaction(a1, a2, 100);
    assertTrue(a1.getTransactions().contains(t1) && a2.getTransactions().contains(t1));
    assertEquals(Transaction.DATE_FORMATTER.format(LocalDateTime.now()), t1.getDateString());
    assertEquals(0, a1.getBalance());
    assertEquals(200, a2.getBalance());

    Transaction t2 = new Transaction((long) 5, a2, a1, 100,
        Transaction.DATE_FORMATTER.format(LocalDateTime.now().plusDays(2)), true, false);
    assertFalse(a1.getTransactions().contains(t2) || a2.getTransactions().contains(t2));
    assertEquals(Transaction.DATE_FORMATTER.format(LocalDateTime.now().plusDays(2)),
        t2.getDateString());
    assertEquals(100, a1.getBalance());
    assertEquals(100, a2.getBalance());

    assertThrows(IllegalArgumentException.class, () -> {
      transaction = new Transaction(a1, a2, -1);
    });

  }

  @Test
  @DisplayName("test commitTransaction with a1 and a2 as param")
  public void testCommitTransaction1() throws IOException {
    transaction = new Transaction((long) 90, a1, a2, 50, true);

    assertEquals(transaction.getFrom(), a1);
    assertEquals(transaction.getReciever(), a2);
    assertEquals(transaction.getAmount(), 50);

    assertEquals(a1.getBalance(), 50);
    assertEquals(a2.getBalance(), 150);
    assertEquals(a1.getTransactions().size(), 1);
    assertEquals(a2.getTransactions().size(), 1);

    Transaction t2 = new Transaction((long) 99, a1, a2, 50.0, false);

    assertEquals(t2.getFrom(), a1);
    assertEquals(t2.getReciever(), a2);
    assertEquals(t2.getAmount(), 50);

    assertEquals(a1.getBalance(), 50);
    assertEquals(a2.getBalance(), 150);
    assertEquals(a1.getTransactions().size(), 2);
    assertEquals(a2.getTransactions().size(), 2);

  }

  @Test
  @DisplayName("test commitTransaction with a1 and null as param")
  public void testCommitTransaction2() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> {
      transaction = new Transaction((long) 69, a1, null, 50, true);
    });

    assertThrows(IllegalStateException.class, () -> {
      transaction = new Transaction((long) 69, a1, a1, 50, true);
    });

    assertEquals(a1.getBalance(), 100);
    assertEquals(a1.getTransactions().size(), 0);

  }

  @Test
  @DisplayName("test equals")
  public void testEquals() {
    System.out.println(a2.getAccountNumber());

    transaction = new Transaction((long) 69, a1, a2, 50, true);

    assertTrue(transaction.equals(transaction));
    assertFalse(transaction.equals(new Transaction((long) 99, a1, a2, 5, false)));
  }

  @Test
  @DisplayName("test different smaller functions")
  public void testSmallerFunctions() {
    Account a3 = new SavingsAccount(new User("username", "email@email.com", "password"), "name");
    a3.deposit(500);
    transaction = new Transaction((long) 1, a1, a2, 50, true);
    assertEquals(Objects.hash((long) 1), transaction.hashCode());

    assertThrows(IllegalArgumentException.class, () -> {
      transaction.setAmount(-500);
    });

    transaction.setAmount(10);
    assertEquals(10, transaction.getAmount());

    assertTrue(transaction.isBetweenDifferentUsers());

    assertThrows(IllegalArgumentException.class, () -> {
      transaction.setId(null);
    });

    transaction.setId((long) 50);
    assertEquals(50, transaction.getId());

    assertAll(() -> assertThrows(IllegalArgumentException.class, () -> {
      transaction.setFrom(null);
    }), () -> assertThrows(IllegalStateException.class, () -> {
      transaction.setFrom(a2);
    }));

    transaction.setFrom(a3);
    assertEquals(a3, transaction.getFrom());

    transaction.setFrom(a1);
    assertAll(() -> assertThrows(IllegalArgumentException.class, () -> {
      transaction.setReciever(null);
    }), () -> assertThrows(IllegalStateException.class, () -> {
      transaction.setReciever(a1);
    }));
    transaction.setFrom(a1);
    transaction.setReciever(a2);
    assertEquals(a2, transaction.getReciever());

  }

  @ParameterizedTest
  @MethodSource("provideInvalidDateStrings")
  public void testConvertToInvalidDate(String dateString) {
    assertThrows(IllegalArgumentException.class, () -> {
      Transaction.convertToDate(dateString);
    });
  }

  private static Stream<Arguments> provideInvalidDateStrings() {
    return Stream.of(Arguments.of("15-10-2021 22:00"), Arguments.of("15/10/2021"),
        Arguments.of("2021-10-15"), Arguments.of("Ugyldig dato"));
  }
}

