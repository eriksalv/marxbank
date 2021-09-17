package it1901;

public class SavingsAccount extends Account {

    public SavingsAccount(String id, User user, double interestRate) {
        super(id, user, interestRate, Account.Type.SAVING);
    }

    public SavingsAccount() {
        super();
    }
}