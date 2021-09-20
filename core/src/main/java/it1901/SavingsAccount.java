package it1901;

public class SavingsAccount extends Account {

    public SavingsAccount(User user, double interestRate) {
        super(user, interestRate);
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