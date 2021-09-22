package it1901;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    /**
     * Bank is on the top of the object hierarchy in the application.
     * Only one bank instance can exist at one time, and the singleton pattern (Lazy implementation)
     * is used to achieve this. 
     */
    
    private static Bank bankInstance = null;

    //Hashtable with account number as key and the corresponding account as value
    private Map<Integer, Account> accounts = new HashMap<Integer, Account>();

    private Bank() {

    }

    public static Bank getInstanceBank() {
        if (bankInstance == null) {
            bankInstance = new Bank();
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
        if (!accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account number does not match any registered account");
        }
        return getAccounts().get(accountNumber);
    }
}
