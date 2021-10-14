package it1901.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    
    private final String id;
    private final Account from;
    private final Account reciever;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String dateString;

    //autoformats the date text-string 
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Initializes transaction object and runs the commitTransaction method.
     * @param id - id of transaction
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     * @param date - date that the transaction took place
     * @param dm - datamanager object for local storage
     * @param commit - commits the transaction of money between accounts if true
    */
    public Transaction(String id, Account from, Account reciever, double amount, String date, boolean commit, boolean add) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        if (date==null) {
            this.transactionDate=LocalDateTime.now();
            this.dateString=DATE_FORMATTER.format(transactionDate);
        } else {
            this.transactionDate = convertToDate(date);
            this.dateString = DATE_FORMATTER.format(transactionDate);
        }
        if(commit) commitTransaction();
        if(add) {
            this.from.addTransaction(this);
            this.reciever.addTransaction(this);
        }
    }

    public Transaction(String id, Account from, Account reciever, double amount, boolean commit, boolean add) {
        this(id, from, reciever, amount, null, commit, add);
    }

    public Transaction(String id, Account from, Account reciever, double amount, boolean commit) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        transactionDate = LocalDateTime.now();
        dateString = DATE_FORMATTER.format(transactionDate);
        if(commit) {
            commitTransaction();
        }
        this.from.addTransaction(this);
        this.reciever.addTransaction(this);
    }

    public Transaction(Account from, Account reciever, double amount) {
        this(UUID.randomUUID().toString(), from, reciever, amount, true);
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
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    /**
     * Checks if a date input is valid according to the formatter of this class
     * @param dateString input date as string
     * @return true if valid, else false.
    */
    public static boolean isValidDate(String dateString) {
        try {
            DATE_FORMATTER.parse(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static LocalDateTime convertToDate(String dateString) {
        if (!isValidDate(dateString)) {
            throw new IllegalArgumentException("Invalid date format");
        }
        String date[] = dateString.split("-");
        return LocalDateTime.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), 0,0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.getId());
    }

}
