package it1901;

import it1901.util.AccountType;

public class SavingsAccount extends Account {

    public SavingsAccount(String id, User user, double interestRate, DataManager dm) {
        super(id, user, interestRate, AccountType.SAVING, dm);
    }

    @Override
    int generateAccountNumber() {
        return (int)Math.round(10000+getInterestRate()); //temporary
    }

    @Override
    String getAccountType() {
        return "Sparekonto";
    } 
}