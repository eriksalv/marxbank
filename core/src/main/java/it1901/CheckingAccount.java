package it1901;

import java.util.Random;

import it1901.util.AccountType;

public class CheckingAccount extends Account {

    public CheckingAccount(String id, User user, double interestRate, DataManager dm) {
        super(id, user, interestRate, AccountType.CHECKING, dm);
    }

    //second constructor with default value of 3 as interest rate
    public CheckingAccount(String id, User user, DataManager dm) {
        super(id, user, 0.5, AccountType.CHECKING, dm);
    }

    public CheckingAccount(User user, DataManager dm, String name) {
        super(user, 0.5, AccountType.CHECKING, dm, name);
    }

    public CheckingAccount(String id, User user, double interestRate, DataManager dm, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.CHECKING, dm, accountNumber, name);
    }

    @Override
    int generateAccountNumber() {
        String accNumberString = "2";
        for (int i=0;i<3;i++) {
            accNumberString += "" + (new Random()).nextInt(10);
        }
        int accNumber = Integer.valueOf(accNumberString);
        if (Bank.getInstanceBank().getAccounts().containsKey(accNumber)) {
            generateAccountNumber();
        }
        return accNumber;
    }

    @Override
    String getAccountType() {
        return "Brukskonto";
    }
    
}
