package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import marxbank.model.Account;
import marxbank.model.CheckingAccount;
import marxbank.model.MarxAccount;
import marxbank.model.SavingsAccount;
import marxbank.model.User;
import marxbank.util.AccountType;

public class AccountFactoryTest {

  private User user;

  @BeforeEach
  public void beforeEach() throws IOException {
    user = new User((long) 1, "username", "email@email.com", "password");
  }

  @Test
  @DisplayName("test createMethod")
  public void testCreateMethod() {
    AccountType bsu = AccountType.get("BSU");
    Account a2 = AccountFactory.create(AccountType.get("Sparekonto"), user, "Ola Nordmann");
    Account a3 = AccountFactory.create(AccountType.get("Brukskonto"), user, "Ola Nordmann");
    Account a4 = AccountFactory.create(AccountType.get("Marxkonto"), user, "Ola Nordmann");

    assertEquals(bsu, null);
    assertTrue(a2 instanceof SavingsAccount);
    assertTrue(a3 instanceof CheckingAccount);
    assertTrue(a4 instanceof MarxAccount);
  }

  @Test
  @DisplayName("test createFromMethod")
  public void testCreateFromMethod() {
    Account a2 =
        AccountFactory.createFrom(AccountType.get("Sparekonto"), (long) 3, user, "Ola Nordmann", 1);
    Account a3 =
        AccountFactory.createFrom(AccountType.get("Brukskonto"), (long) 4, user, "Ola Nordmann", 1);
    Account a4 =
        AccountFactory.createFrom(AccountType.get("Marxkonto"), (long) 5, user, "Ola Nordmann", 1);

    assertTrue(a2 instanceof SavingsAccount);
    assertTrue(a3 instanceof CheckingAccount);
    assertTrue(a4 instanceof MarxAccount);
  }

}
