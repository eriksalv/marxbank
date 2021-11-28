package marxbank.model;

import javax.persistence.Entity;
import marxbank.util.AccountType;

@Entity
public class SavingsAccount extends Account {

  private static double DEFAULT_INTEREST = 3;

  public SavingsAccount() {
    super();
  }

  /**
   * Constructor for class SavingsAccount
   * 
   * @param id of account
   * @param user owner of account
   * @param interestRate interestReate
   */
  public SavingsAccount(Long id, User user, double interestRate) {
    super(id, user, interestRate, AccountType.SAVING);
  }

  /**
   * Constructor for class SavingsAccount
   * 
   * @param id of account
   * @param user owner of account
   */
  public SavingsAccount(Long id, User user) {
    super(id, user, DEFAULT_INTEREST, AccountType.SAVING);
  }

  /**
   * Constructor for class SavingsAccount
   * 
   * @param user owner of account
   * @param name of account
   */
  public SavingsAccount(User user, String name) {
    super(user, DEFAULT_INTEREST, AccountType.SAVING, name);
  }

  /**
   * Constructor for class SavingsAccount
   * 
   * @param id of account
   * @param user owner of account
   * @param interestRate interestRate
   * @param name of account
   * @param accountNumber of account
   */
  public SavingsAccount(Long id, User user, double interestRate, String name, int accountNumber) {
    super(id, user, interestRate, AccountType.SAVING, accountNumber, name);
  }

  /**
   * Generates a account number. For SavingsAccount, the account number always starts with a "1".
   */
  @Override
  public int generateAccountNumber() {
    return Math.toIntExact(this.getId()) + 40000;
  }
}
