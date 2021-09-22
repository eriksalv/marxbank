package it1901;

import java.util.Random;
import java.util.UUID;

import it1901.util.AccountType;

public class SavingsAccount extends Account {

    public SavingsAccount(String id, User user, double interestRate, DataManager dm) {
        super(id, user, interestRate, AccountType.SAVING, dm);
    }

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