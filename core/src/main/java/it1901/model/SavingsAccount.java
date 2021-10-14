package it1901.model;

import java.util.Random;

import it1901.Bank;
import it1901.util.AccountType;

public class SavingsAccount extends Account {

    private final static double DEFAULT_INTEREST = 3;

    public SavingsAccount(String id, User user, double interestRate) {
        super(id, user, interestRate, AccountType.SAVING);
    }

    //second constructor with default value of 3 as interest rate
    public SavingsAccount(String id, User user) {
        super(id, user, DEFAULT_INTEREST, AccountType.SAVING);
    }

    public SavingsAccount(User user, String name) {
        super(user, DEFAULT_INTEREST, AccountType.SAVING, name);
    }

    public SavingsAccount(String id, User user, double interestRate, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.SAVING, accountNumber, name);
    }

    /**
     * Generates a unique account number. For SavingsAccount, the account number always
     * starts with a "1".
     */
    @Override
    public int generateAccountNumber() {
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
    public String getAccountType() {
        return "Sparekonto";
    } 
}