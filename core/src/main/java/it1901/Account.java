package it1901;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import it1901.util.AccountType;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Account {

    @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnoreProperties({"username", "email", "password", "accounts"})
    private User user;
    private final int accountNumber;
    private String name; 
    private String id;
    @JsonIgnoreProperties({"from", "reciever", "amount", "transactionDate", "dateString", "dm"})
    private List<Transaction> transactions = new LinkedList<Transaction>();
    private double balance = 0;
    private double interestRate; //I prosent
    private AccountType type;
    @JsonIgnore
    private DataManager dm;

    public Account(String id, User user, double interestRate, AccountType type, DataManager dm) {
        this.user = user;
        this.interestRate = validateIntereset(interestRate);
        this.id = id;
        this.type = type;
        this.dm = dm;
        this.name = "Ny konto";
        this.accountNumber = generateAccountNumber();

        this.user.addAccount(this);
        this.dm.addAccount(this);
        Bank.getInstanceBank().addAccount(this);
    }

    public Account(String id, User user, double interestRate, AccountType type, DataManager dm, String name) {
        this(id, user, interestRate, type, dm);
        this.name=name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name==null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name=name;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public void deposit(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Deposit must be positive");
		}
		balance+=amount;
        updateAccount();
    }

    public void withdraw(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Must withdraw a positive amount");
		} else if (balance-amount<0) {
			throw new IllegalStateException("Not enough balance on account");
		}
		balance-=amount;
        updateAccount();
    }

    public double validateIntereset(double ir) {
        if(ir < 0) throw new IllegalArgumentException("Interest rate cannot be negative");
        return ir;
    }

    public void addInterest() {
        deposit(getBalance()*getInterestRate()/100);
        updateAccount();
    }

    public AccountType getType() {
        return this.type;
    }

    public void setType(AccountType type) {
        this.type = type;
        updateAccount();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        updateAccount();
    }
    
    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        updateAccount();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public void setInterestRate(double i) {
        this.interestRate = i;
        updateAccount();
    }

    public void setTransactions(List<Transaction> t) {
        this.transactions = t;
        updateAccount();
    }

    public void addTransaction(Transaction t) {
        if (this.transactions.contains(t)) {
            throw new IllegalStateException("Transaction is already registered");
        }
        this.transactions.add(t);
        updateAccount();
    }

    public List<Transaction> getTransactions() {
        return new LinkedList<>(this.transactions);
    }

   private void updateAccount() {
       this.dm.updateAccount(this.id, this);
   }

   @Override
   public int hashCode() {
       return Objects.hash(this.id);
   }

   @Override
   public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Account)) return false;
    Account account = (Account) o;
    if (this.balance != account.getBalance()) return false;
    return Objects.equals(id, account.getId());
   }
    public int getNumberOfTransactions() {
        return getTransactions().size();
    }

    abstract int generateAccountNumber();

    abstract String getAccountType();
    
}
