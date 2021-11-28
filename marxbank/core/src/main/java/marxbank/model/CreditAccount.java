package marxbank.model;

import javax.persistence.Entity;
import marxbank.util.AccountType;

@Entity
public class CreditAccount extends Account {

  private double creditLimit;
  private static double DEFAULT_CREDIT_LIMIT = 200;

  public CreditAccount() {
    super();
  }

  public CreditAccount(User user, String name) {
    super(user, 0, AccountType.CREDIT, name);
    this.creditLimit = DEFAULT_CREDIT_LIMIT;
  }

  public CreditAccount(Long id, User user, double interestRate, String name, int accountNumber) {
    super(id, user, interestRate, AccountType.CREDIT, accountNumber, name);
    this.creditLimit = DEFAULT_CREDIT_LIMIT;
  }

  @Override
  public double getCreditLimit() {
    return this.creditLimit;
  }

  @Override
  public void withdraw(double amount) {
    if (amount <= 0)
      throw new IllegalArgumentException("Withdraw must be positive");
    if ((this.getBalance() + this.creditLimit) - amount < 0)
      throw new IllegalStateException("Not enough balance on account");

    this.setBalance(this.getBalance() - amount);
  }


  @Override
  public int generateAccountNumber() {
    return Math.toIntExact(this.getId()) + 20000;
  }

}
