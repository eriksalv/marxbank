package it1901;

import java.util.LinkedList;
import java.util.List;

public abstract class Account {
    
    private List<Transaction> transactions = new LinkedList<Transaction>();
    private double balance = 0;
    private final double INTEREST_RATE; //I prosent

    public Account(final double interestRate) {
        INTEREST_RATE = interestRate;
    }

    public void deposit(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Deposit must be positive");
		}
		balance+=amount;
    }

    public void withdraw(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Must withdraw a positive amount");
		} else if (balance-amount<0) {
			throw new IllegalStateException("Not enough balance on account");
		}
		balance-=amount;
    }

    public void addInterest() {
        deposit(getBalance()*getInterestRate()/100);
    }
    
    public double getBalance() {
        return this.balance;
    }

    public double getInterestRate() {
        return INTEREST_RATE;
    }
}
