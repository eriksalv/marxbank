package marxbank;

import marxbank.model.Account;
import marxbank.model.CheckingAccount;
import marxbank.model.CreditAccount;
import marxbank.model.MarxAccount;
import marxbank.model.SavingsAccount;
import marxbank.model.User;
import marxbank.util.AccountType;

public class AccountFactory {

  /**
   * AccountFactory is responsible for creating a new Account instance based on the user input
   * (accountType), such that other classes dont need to handle that logic themselves.
   * 
   * @param accountType - input string from user (in norwegian)
   * @param user owner of account
   * @param name name of account
   * @return
   *         <p>
   *         a new account if input is valid, otherwise if none of the text strings match the input
   *         string, null will be returned.
   *         </p>
   */
  public static Account create(AccountType accountType, User user, String name) {
    if (accountType == null) {
      return null;
    }

    switch(accountType) {
      case CHECKING:
        return new CheckingAccount(user, name);
      case CREDIT:
        return new CreditAccount(user, name);
      case MARX:
        return new MarxAccount(user, name);
      case SAVING:
        return new SavingsAccount(user, name);
      default:
        return null;
    }
  }

  public static Account create(AccountType accountType, String name){
    if (accountType == null) {
      return null;
    }

    Account a = null;
    switch(accountType) {
      case CHECKING:
        a = new CheckingAccount();
        a.setName(name);
        a.setInterestRate(0.5);
        a.setType(AccountType.CHECKING);
        break;
      case CREDIT:
        a = new CreditAccount();
        a.setName(name);
        a.setInterestRate(0);
        a.setType(AccountType.CREDIT);
        break;
      case MARX:
        a = new MarxAccount();
        a.setName(name);
        a.setInterestRate(0.01);
        a.setType(AccountType.MARX);
        break;
      case SAVING:
        a = new SavingsAccount();
        a.setName(name);
        a.setInterestRate(3.0);
        a.setType(AccountType.SAVING);
        break;
      default:
        return null;
    }
    return a;
  }

  /**
   * second create method, primarily intended to be used when reading in account from file/database
   * instead of creating a new account
   * 
   * @param accountType type of account
   * @param id id of account
   * @param user owner of account
   * @param name of account
   * @param accountNumber of account
   * @return a new account object if input is valid, otherwise returns null.
   */
  public static Account createFrom(AccountType accountType, Long id, User user, String name,
      int accountNumber) {
    if (accountType == null) {
      return null;
    }

    switch(accountType) {
      case CHECKING:
        return new CheckingAccount(id, user, 0.5, name, accountNumber);
      case CREDIT:
        return new CreditAccount(id, user, 0, name, accountNumber);
      case MARX:
        return new MarxAccount(id, user, 0, name, accountNumber);
      case SAVING:
        return new SavingsAccount(id, user, 3, name, accountNumber);
      default:
        return null;
    }
  }
}
