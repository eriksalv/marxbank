package it1901.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    
    /*The purpose of the transaction class is to store information about a transaction between
    two accounts. The information stored should never be changed, so it essentially functions
    as a record, but it is also responsible for withdrawing and depositing the correct amount
    of balance between the accounts.*/

    private final String id;
    private final Account from;
    private final Account reciever;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String dateString;

    //autoformats the date text-string 
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * Initializes transaction object and runs the commitTransaction method.
     * @param id - id of transaction
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     * @param dm - datamanager object for local storage
     * @param commit - commits the transaction of money between accounts if true
     */
    public Transaction(String id, Account from, Account reciever, double amount, boolean commit, boolean add) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        transactionDate = LocalDateTime.now();
        dateString = dateFormat.format(transactionDate);
        if(commit) commitTransaction();
    }

    public Transaction(String id, Account from, Account reciever, double amount, boolean commit) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        transactionDate = LocalDateTime.now();
        dateString = dateFormat.format(transactionDate);
        if(commit) {
            commitTransaction();
        } else {
            this.from.addTransaction(this);
            this.reciever.addTransaction(this);
        }
    }

    public Transaction(Account from, Account reciever, double amount) {
        this(UUID.randomUUID().toString(), from, reciever, amount, true, true);
    }

    public String getId() {
        return this.id;
    }

    public String getDateString() {
        return dateString;
    }

    public LocalDateTime getTransactionDate() {
        return this.transactionDate;
    }

    public Account getFrom() {
        return from;
    }

    public Account getReciever() {
        return reciever;
    }

    public double getAmount() {
        return this.amount;
    }

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
