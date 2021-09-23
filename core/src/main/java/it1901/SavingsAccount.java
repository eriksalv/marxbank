package it1901;

import java.util.Random;
import java.util.UUID;

import it1901.util.AccountType;

public class SavingsAccount extends Account {

    public SavingsAccount(String id, User user, double interestRate, DataManager dm) {
        super(id, user, interestRate, AccountType.SAVING, dm);
    }

    //second constructor with default value of 3 as interest rate
    public SavingsAccount(String id, User user, DataManager dm) {
        super(id, user, 3, AccountType.SAVING, dm);
    }

    public SavingsAccount(String id, User user, DataManager dm, String name) {
        super(id, user, 3, AccountType.SAVING, dm, name);
    }

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