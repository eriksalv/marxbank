package marxbank.model;

import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;

import marxbank.Bank;
import marxbank.util.AccountType;

@Entity
public class CreditAccount extends Account {

    private double credtiLimit;
    private static double DEFAULT_CREDIT_LIMIT = 200;
    
    public CreditAccount() {
        super();
    }

    public CreditAccount(User user, String name) {
        super(user, 0, AccountType.CREDIT, name);
        this.credtiLimit = DEFAULT_CREDIT_LIMIT;
    }

    public CreditAccount(Long id, User user, double interestRate, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.CREDIT, accountNumber, name);
        this.credtiLimit = DEFAULT_CREDIT_LIMIT;
    }

    @Override
    public double getCreditLimit() {
        return this.credtiLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw must be positive");
        if ((this.getBalance() + this.credtiLimit) - amount < 0) throw new IllegalStateException("Not enough balance on account");
        
        this.setBalance(this.getBalance() - amount);
    }


    @Override
    public int generateAccountNumber() {
        String accNumberString = "69";
        for (int i = 0; i < 3; i++) {
            accNumberString = accNumberString.concat(String.valueOf(ThreadLocalRandom.current().nextInt(10)));
        }
        int accNumber = Integer.parseInt(accNumberString);
        if (Bank.getInstanceBank().getAccounts().containsKey(accNumber)) {
            generateAccountNumber();
        }
        return accNumber;
    }

    @Override
    public String getAccountType() {
        return "Kredittkonto";
    }
    
}
