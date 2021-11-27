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
  public static Account create(String accountType, User user, String name) {
    if ("Sparekonto".equalsIgnoreCase(accountType)) {
      return new SavingsAccount(user, name);
    } else if ("Brukskonto".equalsIgnoreCase(accountType)) {
      return new CheckingAccount(user, name);
    } else if ("Marxkonto".equalsIgnoreCase(accountType)) {
      return new MarxAccount(user, name);
    } else if ("Kredittkonto".equalsIgnoreCase(accountType)) {
      return new CreditAccount(user, name);
    }

    return null;
  }

  public static Account create(String type, String name){
      Account a = null;
        if (AccountType.SAVING.getTypeString().equalsIgnoreCase(type)) {
            a = new SavingsAccount();
            a.setName(name);
            a.setInterestRate(3.0);
            a.setType(AccountType.SAVING);
        } else if (AccountType.CHECKING.getTypeString().equalsIgnoreCase(type)) {
            a = new CheckingAccount();
            a.setName(name);
            a.setInterestRate(0.5);
            a.setType(AccountType.CHECKING);
        } else if (AccountType.CREDIT.getTypeString().equalsIgnoreCase(type)) {
            a = new CreditAccount();
            a.setName(name);
            a.setInterestRate(0);
            a.setType(AccountType.CREDIT);
        } else if (AccountType.MARX.getTypeString().equalsIgnoreCase(type)) {
            a = new MarxAccount();
            a.setName(name);
            a.setInterestRate(0.01);
            a.setType(AccountType.MARX);
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
  public static Account createFrom(String accountType, Long id, User user, String name,
      int accountNumber) {
    if ("Sparekonto".equalsIgnoreCase(accountType)) {
      return new SavingsAccount(id, user, 3, name, accountNumber);
    } else if ("Brukskonto".equalsIgnoreCase(accountType)) {
      return new CheckingAccount(id, user, 0.5, name, accountNumber);
    } else if ("Marxkonto".equalsIgnoreCase(accountType)) {
      return new MarxAccount(id, user, 0, name, accountNumber);
    } else if ("Kredittkonto".equalsIgnoreCase(accountType)) {
      return new CreditAccount(id, user, 0, name, accountNumber);
    }

    return null;
  }
}
