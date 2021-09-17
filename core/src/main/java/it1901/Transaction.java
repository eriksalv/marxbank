package it1901;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Transaction {
    
    private String id;
    private Account from;
    private Account reciever;
    private double amount;
    private LocalDateTime transactionDate;
    private String dateString;

    //autoformats the date to a readable norwegian text-string 
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Transaction() {}

    /**
     * Initializes transaction object and runs the commitTransaction method.
     * 
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     */
    public Transaction(String id, Account from, Account reciever, double amount) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = amount;
        transactionDate = LocalDateTime.now();
        dateString = dateFormat.format(transactionDate);
        commitTransaction();
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

    public void setTransactionDate(LocalDateTime date) {
        this.transactionDate = date;
        this.dateString = dateFormat.format(date);
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getReciever() {
        return reciever;
    }

    public void setReciever(Account reciever) {
        this.reciever = reciever;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    /**
     * static function to save passed in Transaction object
     * @param transaction to save
     * @throws IllegalArgumentException if transaction is null
     * @throws IllegalArgumentException if transction object fields are null
     */
    public static void saveTransaction(Transaction transaction) {
        if (transaction == null) throw new IllegalArgumentException("Transaction cannot be null.");
        if (transaction.getFrom() == null || transaction.getReciever() == null || transaction.getDateString() == null) throw new IllegalArgumentException("Transaction fields cannot be null");
    
        ObjectMapper objectMapper = new ObjectMapper();
        File transactionFile = new File(String.format("../data/transactions/%s.%s.%s.json", transaction.getId(), transaction.getFrom().getId(), transaction.getDateString().replaceAll("\\s", "")));

        try {
            objectMapper.writeValue(transactionFile, transaction);
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) {
                try {
                    transactionFile = new File(String.format("data/transactions/%s.%s.%s.json", transaction.getId(), transaction.getFrom().getId(), transaction.getDateString().replaceAll("\\s", "")));
                    objectMapper.writeValue(transactionFile, transaction);
                } catch (Exception a) {
                    System.out.println(String.format("Error: %s", a));
                }
            } else {
                System.out.println(String.format("Error: %s", e));
            }
        }
    }


    /**
     * saves the transaction object
     * @throws IllegalStateException if one ore more of the objects fields are null
     */
    public void saveTransaction() {
        if (this.from == null || this.reciever == null || this.dateString == null) throw new IllegalStateException("From, Reciever and date cannot be empty");
        ObjectMapper objectMapper = new ObjectMapper();
        File transactionFile = new File(String.format("../data/transactions/%s.%s.%s.json", this.id, this.from.getId(), this.dateString.replaceAll("\\s", "").replaceAll(":", ".")));

        try {
            objectMapper.writeValue(transactionFile, this);
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) {
                try {
                    transactionFile = new File(String.format("data/transactions/%s.%s.%s.json", this.id, this.from.getId(), this.dateString.replaceAll("\\s", "").replaceAll(":", ".")));
                    objectMapper.writeValue(transactionFile, this);
                } catch (Exception a) {
                    System.out.println(String.format("Error: %s", a));
                }
            } else {
                System.out.println(String.format("Error: %s", e));
            }
        }
    }

    // public static void main(String[] args) {
    //     User u = new User("1", "testUser", "test@email.com", "password");
    //     User u2 = new User("2", "testUser2", "test2@email.com", "password2");

    //     Account a1 = new SavingsAccount("1", u, 2.0);
    //     Account a2 = new SavingsAccount("2", u2, 3.0);

    //     u.addAccount(a1);
    //     a1.deposit(5000);
    //     u2.addAccount(a2);

    //     Transaction t = new Transaction("1", a1, a2, 500);

    //     u.saveUser();
    //     u2.saveUser();
    //     a1.saveAccount();
    //     a2.saveAccount();
    //     t.saveTransaction();
    // }    
}
