package marxbank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
/**
* The purpose of the transaction class is to store information about a transaction between
* two accounts. The information stored should never be changed, so it essentially functions
* as a record, but it is also responsible for withdrawing and depositing the correct amount
* of balance between the accounts.
*/
@Entity
public class Transaction {
    
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(targetEntity = Account.class)
    private Account from;
    @ManyToOne(targetEntity = Account.class)
    private Account reciever;
    @Column
    private double amount;
    @Transient
    private LocalDateTime transactionDate;
    @Column
    private String dateString;

    //autoformats the date text-string 
    @Transient
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Transaction() {
        this.transactionDate=LocalDateTime.now();
        this.dateString=DATE_FORMATTER.format(transactionDate);
    }

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
    public Transaction(Long id, Account from, Account reciever, double amount, String date, boolean commit, boolean add) {
        this.id = id;
        setFrom(from);
        setReciever(reciever);
        setAmount(amount);
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
    public Transaction(Long id, Account from, Account reciever, double amount, boolean commit, boolean add) {
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
    public Transaction(Long id, Account from, Account reciever, double amount, boolean commit) {
        this(id, from, reciever, amount, null, commit, true);
    }

    /**
     * Compact transaction constructor that generates id automatically, uses current time
     * as date, commits transaction and adds transaction to from and recievers
     * transaction list, id is generated with UUID
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
    */
    public Transaction(Account from, Account reciever, double amount) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        setFrom(from);
        setReciever(reciever);
        this.amount = validateAmount(amount);
        this.transactionDate=LocalDateTime.now();
        this.dateString=DATE_FORMATTER.format(transactionDate);
        commitTransaction();
        this.from.addTransaction(this);
        this.reciever.addTransaction(this);
    }

    /**
     * Getter for unique transaction id
     * @return transaction id
    */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setFrom(Account from) {
        this.from = validateFrom(from);
    }

    /**
     * From account cannot be null or equal to reciever
     * @param from 
     * @return
     * @throws IllegalArgumentException if account is null
     * @throws IllegalStateException if account is equal to reciever
     */
    private Account validateFrom(Account from) {
        if (from == null) {
            throw new IllegalArgumentException("Sender account cannot be null");
        } else if (from.equals(reciever)) {
            throw new IllegalStateException("Cannot transfer between the same account");
        }
        return from;
    }

    /**
     * Getter for reciever account
     * @return reciever
    */
    public Account getReciever() {
        return reciever;
    }

    public void setReciever(Account reciever) {
        this.reciever = validateReciever(reciever);
    }

    /**
     * Reciever account cannot be null or equal to from
     * @param from 
     * @return
     * @throws IllegalArgumentException if account is null
     * @throws IllegalStateException if account is equal to from
     */
    private Account validateReciever(Account reciever) {
        if (reciever == null) {
            throw new IllegalArgumentException("Reciever account cannot be null");
        } else if (reciever.equals(from)) {
            throw new IllegalStateException("Cannot transfer between the same account");
        }
        return reciever;
    }

    /**
     * Getter for the amount of money in transaction
     * @return amount
    */
    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = validateAmount(amount);
    }

    /**
     * checks if the transaction is between different users or not
     * (used in backend).
     * @return true if different users
     */
    public boolean isBetweenDifferentUsers() {
        return !this.from.getUser().equals(this.reciever.getUser());
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
     * accounts.
     */  
    public void commitTransaction() {
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
