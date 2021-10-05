package it1901;

import java.util.Random;

import it1901.util.AccountType;

public class CreditAccount extends Account {

    private double credtiLimit;
    private static final double DEFAULT_CREDIT_LIMIT = 200;
    
    public CreditAccount(User user, DataManager dm, String name) {
        super(user, 0, AccountType.CREDIT, dm, name);
        this.credtiLimit = DEFAULT_CREDIT_LIMIT;
    }

    public CreditAccount(String id, User user, double interestRate, DataManager dm, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.CREDIT, dm, accountNumber, name);
        this.credtiLimit = DEFAULT_CREDIT_LIMIT;
    }

    public double getCreditLimit() {
        return this.credtiLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw must be positive");
        if((this.getBalance() + this.credtiLimit) - amount < 0) throw new IllegalStateException("Not enough balance on account");
        
        this.setBalance(this.getBalance() - amount);
        updateAccount();
    }


    @Override
    int generateAccountNumber() {
        String accNumberString = "69";
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
        return "Kredittkonto";
    }
    
}
