package marxbank.model;

import javax.persistence.Entity;
import marxbank.util.AccountType;

@Entity
public class CheckingAccount extends Account {

  private static final double DEFAULT_INTEREST = 0.5;

  public CheckingAccount() {
    super();
  }

  public CheckingAccount(Long id, User user, double interestRate) {
    super(id, user, interestRate, AccountType.CHECKING);
  }

  // second constructor with default value of 0.5 as interest rate
  public CheckingAccount(Long id, User user) {
    super(id, user, DEFAULT_INTEREST, AccountType.CHECKING);
  }

  public CheckingAccount(User user, String name) {
    super(user, DEFAULT_INTEREST, AccountType.CHECKING, name);
  }

  public CheckingAccount(Long id, User user, double interestRate, String name, int accountNumber) {
    super(id, user, interestRate, AccountType.CHECKING, accountNumber, name);
  }

  @Override
  public int generateAccountNumber() {
    return Math.toIntExact(this.getId()) + 10000;
  }

}
