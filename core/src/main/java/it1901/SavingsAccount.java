package it1901;

import java.util.Random;

import it1901.util.AccountType;

public class SavingsAccount extends Account {

    private final static double DEFAULT_INTEREST = 3;

    /**
     * Constructor for savingsAccount
     * Should only be used by AccountDeserializer such that a new account cannot be generate with the same id
     * @param id id of account
     * @param user owner of account
     * @param interestRate interestrate of account
     * @param dm DataManager Object
     */
    public SavingsAccount(String id, User user, double interestRate, DataManager dm) {
        super(id, user, interestRate, AccountType.SAVING, dm);
    }

    /**
     * Contsructor for savingsAccount with default interestRate
     * @param id of account
     * @param user owner of account
     * @param dm DataManager object
     */
    public SavingsAccount(String id, User user, DataManager dm) {
        super(id, user, DEFAULT_INTEREST, AccountType.SAVING, dm);
    }

    /**
     * Constructor for savingsAccount with automatic id generation
     * @param user owner of account
     * @param dm DataManager object
     * @param name of account
     */
    public SavingsAccount(User user, DataManager dm, String name) {
        super(user, DEFAULT_INTEREST, AccountType.SAVING, dm, name);
    }

    /**
     * Constructor for savingsaccount with all paramaters avaliable
     * @param id of account
     * @param user owner of account
     * @param interestRate interest rate of account
     * @param dm DataManager object
     * @param name of account
     * @param accountNumber account number of account
     */
    public SavingsAccount(String id, User user, double interestRate, DataManager dm, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.SAVING, dm, accountNumber, name);
    }

    /**
     * Generates a unique account number. For SavingsAccount, the account number always
     * starts with a "1".
     */
    @Override
    int generateAccountNumber() {
        String accNumberString = "1";
        for (int i=0;i<3;i++) {
            accNumberString += "" + (new Random()).nextInt(10);
        }
        int accNumber = Integer.valueOf(accNumberString);
        if (Bank.getInstanceBank().getAccounts().containsKey(accNumber)) {
            generateAccountNumber();
        }
        return accNumber; //temporary
    }

    @Override
    String getAccountType() {
        return "Sparekonto";
    } 
}