package marxbank.model;

import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;

import marxbank.Bank;
import marxbank.util.AccountType;

@Entity
public class CheckingAccount extends Account {

    private static final double DEFAULT_INTEREST = 0.5;

    protected CheckingAccount() {
        super();
    }

    public CheckingAccount(Long id, User user, double interestRate) {
        super(id, user, interestRate, AccountType.CHECKING);
    }

    //second constructor with default value of 0.5 as interest rate
    public CheckingAccount(Long id, User user) {
        super(id, user, DEFAULT_INTEREST, AccountType.CHECKING);
    }

    public CheckingAccount(User user, String name) {
        super(user, DEFAULT_INTEREST, AccountType.CHECKING, name);
    }

    public CheckingAccount(Long id, User user, double interestRate, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.CHECKING, accountNumber, name);
    }

    @Override
    public int generateAccountNumber() {
        String accNumberString = "2";
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
        return "Brukskonto";
    }
    
}
