package it1901;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

import it1901.util.AccountType;

/**
    * MarxAccount is a special type of account that has a maximum balance that cannot be exceeded
    * when depositing to the account. If a deposit to a MarxAccount results in a higher balance than
    * the max value, a new transaction will be created that transfers the excess amount to an account
    * with the lowest registered balance.
*/
public class MarxAccount extends Account {

    private final static double MAX_BALANCE = 500;
    private final static double DEFAULT_INTEREST = 0.01;

    public MarxAccount(String id, User user, double interestRate, DataManager dm) {
        super(id, user, interestRate, AccountType.MARX, dm);
    }

    //second constructor with default value of 0 as interest rate
    public MarxAccount(String id, User user, DataManager dm) {
        super(id, user, DEFAULT_INTEREST, AccountType.MARX, dm);
    }

    public MarxAccount(User user, DataManager dm, String name) {
        super(user, DEFAULT_INTEREST, AccountType.MARX, dm, name);
    }

    public MarxAccount(String id, User user, double interestRate, DataManager dm, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.MARX, dm, accountNumber, name);
    }

    /**
     * generates a unique account number starting with "48"
     */
    @Override
    int generateAccountNumber() {
        String accNumberString = "48";
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
        return "Marxkonto";
    }

    /**
     * Deposits the specified amount, and then checks if max balance has been exceeded.
     * If that is the case, creates a new transaction instance from this to the account
     * with the lowest registered balance to get rid of the excess amount.
     */
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        if (this.getBalance()>MAX_BALANCE) {
            double newAmount = getBalance()-MAX_BALANCE; //how much to send away

            //Account to send to (cannot be another marx account).
            //Finds the account with the lowest balance.
            Account reciever = Bank.getInstanceBank().getAccounts().values().stream()
            .filter(acc -> !(acc instanceof MarxAccount))
            .min(Comparator.comparing(Account::getBalance))
            .orElseThrow(NoSuchElementException::new);

            Transaction t = new Transaction(this, reciever, newAmount, this.dm);
        }
    }
}
