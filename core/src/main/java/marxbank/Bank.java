package marxbank;

import java.util.HashMap;
import java.util.Map;

import marxbank.model.Account;
/**
 * Bank is on the top of the object hierarchy in the application.
 * Only one bank instance can exist at one time, and the singleton pattern
 * (Thread safe with double checked locking) is used to achieve this. 
 */

public final class Bank {

    private static Bank bankInstance = null;

    //Hashtable with account number as key and the corresponding account as value
    private Map<Integer, Account> accounts = new HashMap<Integer, Account>();

    private Bank() {
        System.out.println("instance created");
    }

    public static Bank getInstanceBank() {
        if (bankInstance == null) {
            synchronized (Bank.class) {
                if (bankInstance == null) {
                    bankInstance = new Bank();
                }
            }
        }
        return bankInstance;
    }

    public Map<Integer, Account> getAccounts() {
        return new HashMap<Integer, Account>(accounts);
    }

    public void addAccount(Account a) {
        accounts.put(a.getAccountNumber(), a);
    }

    public Account getAccount(int accountNumber) {
        if (!accounts.containsKey(accountNumber))
            throw new IllegalArgumentException("Account number does not match any registered account");
        return getAccounts().get(accountNumber);
    }

    public void clearAccounts() {
        accounts = new HashMap<Integer, Account>();
    }

}
