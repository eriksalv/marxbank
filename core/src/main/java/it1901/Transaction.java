package it1901;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Transaction {
    
    /*The purpose of the transaction class is to store information about a transaction between
    two accounts. The information stored should never be changed, so it essentially functions
    as a record, but it is also responsible for withdrawing and depositing the correct amount
    of balance between the accounts.*/

    private String id;
    @JsonIgnoreProperties({"user", "transactions", "balance", "interestRate", "type", "dm"})
    private final Account from;
    @JsonIgnoreProperties({"user", "transactions", "balance", "interestRate", "type", "dm"})
    private final Account reciever;
    private final double amount;
    @JsonIgnore
    private final LocalDateTime transactionDate;
    private final String dateString;
    @JsonIgnore
    private DataManager dm;

    //autoformats the date text-string 
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * Initializes transaction object and runs the commitTransaction method.
     * 
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     * @param commit - commit the transaction
     */
    public Transaction(String id, Account from, Account reciever, double amount, DataManager dm) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        transactionDate = LocalDateTime.now();
        dateString = dateFormat.format(transactionDate);
        this.dm = dm;
        commitTransaction();
        /*else {
            this.from.addTransaction(this);
            this.reciever.addTransaction(this);
        }
        this.dm.addTransaction(this);*/
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateString() {
        return dateString;
    }

    public LocalDateTime getTransactionDate() {
        return this.transactionDate;
    }

    /*public void setTransactionDate(LocalDateTime date) {
        this.transactionDate = date;
        this.dateString = dateFormat.format(date);
        updateTransaction();
    }*/

    public Account getFrom() {
        return from;
    }

   /* public void setFrom(Account from) {
        this.from = from;
        updateTransaction();
    }*/

    public Account getReciever() {
        return reciever;
    }

    /*public void setReciever(Account reciever) {
        this.reciever = reciever;
        updateTransaction();
    }*/

    public double getAmount() {
        return this.amount;
    }

    /*public void setAmount(double amount) {
        System.out.println(amount);
        this.amount = amount;
        updateTransaction();
    }*/

    public double validateAmount(double amount) {
        if(amount < 1) throw new IllegalArgumentException("Amount cannot be negative");
        return amount;
    }

    /**
     * Commits transaction by withdrawing from and depositing to the respective
     * accounts, and adding this transaction object to their history.
     * 
     * @exception - Throws IllegalStateException if either of the accounts are null 
     */    
    public void commitTransaction() {
        if (from == null || reciever == null) {
            throw new IllegalStateException("Cannot commit transaction");
        }
        from.withdraw(this.amount);
        reciever.deposit(this.amount);
        from.addTransaction(this);
        reciever.addTransaction(this);
    }

    /*private void updateTransaction() {
        this.dm.updateTransaction(this.id, this);
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.getId());
    }

}
