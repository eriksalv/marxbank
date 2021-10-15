package marxbank.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

import marxbank.Bank;
import marxbank.util.AccountType;

/**
    * MarxAccount is a special type of account that has a maximum balance that cannot be exceeded
    * when depositing to the account. If a deposit to a MarxAccount results in a higher balance than
    * the max value, a new transaction will be created that transfers the excess amount to an account
    * with the lowest registered balance.
*/
public class MarxAccount extends Account {

    public static final double MAX_BALANCE = 500;
    public static final double DEFAULT_INTEREST = 0.01;

    /**
     * MarxAccount "default" constructor
     * @param user owner of account
     * @param name of account
     */
    public MarxAccount(User user, String name) {
        super(user, DEFAULT_INTEREST, AccountType.MARX, name);
    }
    
    /**
     * Constructor for class MarxAccount
     * @param id of account
     * @param user owner of account
     * @param interestRate of account
     */
    public MarxAccount(String id, User user, double interestRate) {
        super(id, user, interestRate, AccountType.MARX);
    }

   /**
    * Constructor for class MarxAccount
    * @param id of account
    * @param user owner of account
    */
    public MarxAccount(String id, User user) {
        super(id, user, DEFAULT_INTEREST, AccountType.MARX);
    }

    /**
     * Constructor for class MarxAccount
     * @param id of account
     * @param user owner of account
     * @param interestRate intereserRate
     * @param name of account
     * @param accountNumber of account
     */
    public MarxAccount(String id, User user, double interestRate, String name, int accountNumber) {
        super(id, user, interestRate, AccountType.MARX, accountNumber, name);
    }

    /**
     * generates a unique account number starting with "48"
     */
    @Override
    public int generateAccountNumber() {
        Random rand = new SecureRandom();
        String accNumberString = "48";
        for (int i=0;i<3;i++) {
            accNumberString += String.valueOf(rand.nextInt(10));
        }
        int accNumber = Integer.parseInt(accNumberString);
        if (Bank.getInstanceBank().getAccounts().containsKey(accNumber)) {
            generateAccountNumber();
        }
        return accNumber;
    }

    @Override
    public String getAccountType() {
        return "Marxkonto";
    }

    /**
     * Deposits the specified amount, and then checks if max balance has been exceeded.
     * If that is the case, creates a new transaction instance from this to the account
     * with the lowest registered balance to get rid of the excess amount. If such an account
     * cannot be found, the balance will be kept.
     */
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        if (this.getBalance() > MAX_BALANCE) {
            double newAmount = getBalance() - MAX_BALANCE; //how much to send away

            //Account to send to (cannot be another marx account).
            //Finds the account with the lowest balance.
            Account reciever = Bank.getInstanceBank().getAccounts().values().stream()
            .filter(acc -> !(acc instanceof MarxAccount))
            .min(Comparator.comparing(Account::getBalance)).orElse(null);

            // If no appropriate reciever can be found, the balance is kept
            if (reciever == null) {
                return;
            }

            Transaction t = new Transaction(this, reciever, newAmount);
        }
    }
}
