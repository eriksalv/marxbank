package marxbank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.UUID;
/**
* The purpose of the transaction class is to store information about a transaction between
* two accounts. The information stored should never be changed, so it essentially functions
* as a record, but it is also responsible for withdrawing and depositing the correct amount
* of balance between the accounts.
*/

public class Transaction {
    
    private final String id;
    private final Account from;
    private final Account reciever;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String dateString;

    //autoformats the date text-string 
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Initializes transaction object and runs the commitTransaction method if commit is true.
     * @param id - id of transaction
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     * @param date - date that the transaction took place
     * @param dm - datamanager object for local storage
     * @param commit - commits the transaction of money between accounts if true
     * @param add - adds this transaction to from and recievers transaction list if true
    */
    public Transaction(String id, Account from, Account reciever, double amount, String date, boolean commit, boolean add) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        if (date == null) {
            this.transactionDate=LocalDateTime.now();
            this.dateString=DATE_FORMATTER.format(transactionDate);
        } else {
            this.transactionDate = convertToDate(date);
            this.dateString = DATE_FORMATTER.format(transactionDate);
        }
        if (commit) commitTransaction();
        if (add) {
            this.from.addTransaction(this);
            this.reciever.addTransaction(this);
        }
    }

    /**
     * Initializes transaction object and runs the commitTransaction method if commit is true, 
     * with the current time as date
     * @param id - id of transaction
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     * @param dm - datamanager object for local storage
     * @param commit - commits the transaction of money between accounts if true
     * @param add - adds this transaction to from and recievers transaction list if true
    */
    public Transaction(String id, Account from, Account reciever, double amount, boolean commit, boolean add) {
        this(id, from, reciever, amount, null, commit, add);
    }

    /**
     * Initializes transaction object and runs the commitTransaction method if commit is true,
     * with the current time as date
     * and adds this transaction to from and recievers transaction list
     * @param id - id of transaction
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     * @param dm - datamanager object for local storage
     * @param commit - commits the transaction of money between accounts if true
    */
    public Transaction(String id, Account from, Account reciever, double amount, boolean commit) {
        this(id, from, reciever, amount, null, commit, true);
    }

    /**
     * Compact transaction constructor that generates id automatically, uses current time
     * as date, commits transaction and adds transaction to from and recievers
     * transaction list
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
    */
    public Transaction(Account from, Account reciever, double amount) {
        this(UUID.randomUUID().toString(), from, reciever, amount, null, true, true);
    }

    /**
     * Getter for unique transaction id
     * @return transaction id
    */
    public String getId() {
        return this.id;
    }

    /**
     * Getter for string representation of transaction date, which
     * is useful for representing dates in the UI.
     * @return dateString
    */
    public String getDateString() {
        return dateString;
    }

    /**
     * Getter for LocalDateTime representation of date, useful
     * for comparing dates against eachother and getting 
     * the current date by calling LocalDateTime.now().
     * @return transactionDate
    */
    public LocalDateTime getTransactionDate() {
        return this.transactionDate;
    }

    /**
     * Getter for the account that money is transferred from
     * @return from
    */
    public Account getFrom() {
        return from;
    }

    /**
     * Getter for reciever account
     * @return reciever
    */
    public Account getReciever() {
        return reciever;
    }

    /**
     * Getter for the amount of money in transaction
     * @return amount
    */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Makes sure that the amount transferred is non-negative.
     * @param amount - Amount of money in transaction
     * @return amount
     * @throws IllegalArgumentException
    */
    public double validateAmount(double amount) {
        if(amount <= 0) throw new IllegalArgumentException("Amount cannot be negative");
        return amount;
    }

    /**
     * Commits transaction by withdrawing from and depositing to the respective
     * accounts, and adding this transaction object to their history.
     * 
     * @throws IllegalStateException if either of the accounts are null 
    */    
    public void commitTransaction() {
        if (from == null || reciever == null) {
            throw new IllegalStateException("Cannot commit transaction");
        } else if (from.equals(reciever)) {
            throw new IllegalArgumentException("Cannot transfer between the same account");
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
     * @param dateString - input date as string
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

    /**
     * Converts a string representation of a date to LocalDateTime, given
     * that it is represented correctly according to the formatter of this class.
     * @param dateString - String representation of date
     * @return LocalDateTime object of the given date string
     * @throws IllegalArgumentException If the date format is invalid
    */
    public static LocalDateTime convertToDate(String dateString) {
        if (!isValidDate(dateString)) {
            throw new IllegalArgumentException("Invalid date format");
        }
        String[] date = dateString.split("-");
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
