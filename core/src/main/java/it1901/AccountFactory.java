package it1901;

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
    public static Account create(String accountType, User user, DataManager dm, String name) {
        if ("Sparekonto".equalsIgnoreCase(accountType)) {
            return new SavingsAccount(user, dm, name);
        } else if ("Duck".equalsIgnoreCase(accountType)) {
            return null;
        }

        return null;
    }


    public static Account createFrom(String accountType, String id, User user, DataManager dm, String name, int accountNumber) {
        if ("Sparekonto".equalsIgnoreCase(accountType)) {
            return new SavingsAccount(id, user,3, dm, name, accountNumber);
        } else if ("Duck".equalsIgnoreCase(accountType)) {
            return null;
        }

        return null;
    }
    
}
