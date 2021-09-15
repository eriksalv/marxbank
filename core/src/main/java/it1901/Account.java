package it1901;

import java.util.LinkedList;
import java.util.List;

public abstract class Account {
    
    private final User user;
    private List<Transaction> transactions = new LinkedList<Transaction>();
    private double balance = 0;
    private final double interestRate; //I prosent

    public Account(User user, double interestRate) {
        this.user = user;
        this.interestRate = interestRate;
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

    public User getUser() {
        return this.user;
    }
    
    public double getBalance() {
        return this.balance;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public void addTransaction(Transaction t) {
        if (this.transactions.contains(t)) {
            throw new IllegalStateException("Transaction is already registered");
        }
        this.transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return new LinkedList<>(this.transactions);
    }
}
