package marxbank;

import marxbank.model.Account;
import marxbank.model.CheckingAccount;
import marxbank.model.CreditAccount;
import marxbank.model.MarxAccount;
import marxbank.model.SavingsAccount;
import marxbank.model.User;

public class AccountFactory {
    
    /**
     * AccountFactory is responsible for creating a new Account instance based on the user input (accountType),
     * such that other classes dont need to handle that logic themselves.  
     * @param accountType - input string from user (in norwegian)
     * @param user 
     * @param dm
     * @param name
     * @return - if none of the text strings match the input string, null will be returned
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

    /**
     * second create method, primarily intended to be used when reading in account from file/database instead of creating a new account
     * @param accountType
     * @param id
     * @param user
     * @param dm
     * @param name
     * @param accountNumber
     * @return
     */
    public static Account createFrom(String accountType, long id, User user, String name, int accountNumber) {
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

    /**
     * Makes a copy of an account object
     * @param a - account to copy
     * @return Account
    */
    public static Account copy(Account a) {
        return createFrom(a.getAccountType(), a.getId(), a.getUser(), a.getName(), a.getAccountNumber());
    }
    
}
