package it1901;

public class SavingsAccount implements IAccount {

    private double balance = 0;
    private final double INTEREST_RATE; //I prosent

    public SavingsAccount(final double interestRate) {
        INTEREST_RATE = interestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Deposit must be positive");
		}
		balance+=amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Must withdraw a positive amount");
		} else if (balance-amount<0) {
			throw new IllegalStateException("Not enough balance on account");
		}
		balance-=amount;
    }

    public void addInterest() {
        balance+=balance*INTEREST_RATE/100;
    }
    
    @Override
    public double getBalance() {
        return this.balance;
    }

    public double getInterestRate() {
        return INTEREST_RATE;
    }
}
