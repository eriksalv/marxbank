package marxbank.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import marxbank.Bank;
import marxbank.util.AccountType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id @GeneratedValue
    private Long id;
    @Column
    private int accountNumber;
    @Column
    private AccountType type;
    @ManyToOne
    private User user;
    @Column
    private String name; 
    @OneToMany(targetEntity = Transaction.class)
    private List<Transaction> transactions = new LinkedList<Transaction>();
    @Column
    private double balance = 0;
    @Column
    private double interestRate; // In percent

    protected Account() {}

    /**
     * Constructur for class Account.
     * @param id2 - unique id
     * @param user - User that owns this account
     * @param interestRate - Accounts interest rate in percent
     * @param type - Account type
    */
    public Account(Long id2, User user, double interestRate, AccountType type) {
        this.user = user;
        this.interestRate = validateIntereset(interestRate);
        this.id = id2;
        this.type = type;
        this.name = "Ny konto";
        this.accountNumber = generateAccountNumber();
        Bank.getInstanceBank().addAccount(this);
        this.user.addAccount(this);
    }

    /**
     * Constructur for class Account with existing account number, instead of generating new.
     * @param id - unique id
     * @param user - User that owns this account
     * @param interestRate - Accounts interest rate in percent
     * @param type - Account type
     * @param accountNumber - Existing account number 
     * @param name - Name of the account
    */
    public Account(Long id, User user, double interestRate, AccountType type, int accountNumber, String name) {
        this.user = user;
        this.interestRate = validateIntereset(interestRate);
        this.id = id;
        this.type = type;
        this.name = "Ny konto";
        this.accountNumber = accountNumber;
        this.name = name;
        this.user.addAccount(this);
        Bank.getInstanceBank().addAccount(this);
    }

    /**
     * Constructur for class Account.
     * @param id - unique id
     * @param user - User that owns this account
     * @param interestRate - Accounts interest rate in percent
     * @param type - Account type
     * @param name - Name of the account
    */
    public Account(User user, double interestRate, AccountType type, String name) {
        this(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, user, interestRate, type);
        this.name = name;
    }

    /**
     * Getter for account name, does not have to be unique
     * @return name
    */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for account name
     * @param name - name of account
     * @throws IllegalArgumentException if name is null
    */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    /**
     * Getter for unique account number
     * @return
    */
    public int getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Deposits an amount to this account.
     * @param amount - amount to deposit
     * @exception IllegalArgumentException if amount is negative.
    */
    public void deposit(double amount) {
        if (amount <= 0) {
		    throw new IllegalArgumentException("Deposit must be positive");
	    }
		balance += amount;
    }

    /**
     * Withdraw an amount from this account.
     * @param amount
     * @exception IllegalArgumentException if amount is negative.
     * @exception IllegalStateException if it is not enough balance on the account.
     */
    public void withdraw(double amount) {
        if (amount <=0 ) {
			throw new IllegalArgumentException("Must withdraw a positive amount");
		} else if (balance - amount < 0) {
			throw new IllegalStateException("Not enough balance on account");
		}
		balance -= amount;
    }

    /**
     * Checks if interest rate is positive.
     * @param ir interestRate
     * @return interest rate.
    */
    public double validateIntereset(double ir) {
        if(ir < 0) throw new IllegalArgumentException("Interest rate cannot be negative");
        return ir;
    }

    /**
     * Adds interest rate to the balance on this account.
    */
    public void addInterest() {
        deposit(getBalance() * getInterestRate() / 100);
    }

    public AccountType getType() {
        return this.type;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        this.user = user;
    }
    
    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        if (id == null) throw new IllegalArgumentException("new Id cannot be null");
        this.id = id;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public void setInterestRate(double i) {
        this.interestRate = i;
    } 

    public void setTransactions(List<Transaction> t) {
        this.transactions = t;
    }

    /**
     * Adds transaction in the list "transactions"
     * @param t Transaction to add
     * @exception IllegalStateException if the transaction already is registered.
    */
    public void addTransaction(Transaction t) {
        if (this.transactions.contains(t)) {
            throw new IllegalStateException("Transaction is already registered");
        }
        this.transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return new LinkedList<>(this.transactions);
    }

    public double getCreditLimit() {
        return 0;
    }

    @Override
    public int hashCode() {
       return Objects.hash(this.id);
   }

   @Override
   public boolean equals(Object o) {
       double epsilon = 0.0000001;
       if (this == o) return true;
       if (!(o instanceof Account)) return false;
       Account account = (Account) o;
       if(Math.abs(this.getBalance() - account.getBalance()) < epsilon) return false;
       if (this.accountNumber != account.getAccountNumber()) return false;
       return Objects.equals(id, account.getId());
   }
    public int getNumberOfTransactions() {
        return getTransactions().size();
    }

    public abstract int generateAccountNumber();

    public abstract String getAccountType();
    
}
