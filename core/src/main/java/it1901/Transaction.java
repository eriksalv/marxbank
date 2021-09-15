package it1901;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Transaction {
    
    private final Account from;
    private final Account reciever;
    private final double amount;
    private final Date transactionDate;
    private final String dateString;

    //autoformats the date to a readable norwegian text-string 
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss", new Locale("nb"));
    
    /**
     * Initializes transaction object and runs the commitTransaction method.
     * 
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     */
    public Transaction(Account from, Account reciever, double amount) {
        this.from=from;
        this.reciever=reciever;
        this.amount=amount;
        transactionDate=new Date();
        dateString=dateFormat.format(transactionDate);
        commitTransaction();
    }

    public String getDateString() {
        return dateString;
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

    public static void main(String[] args) {
        Transaction t = new Transaction(new SavingsAccount(2), new SavingsAccount(2), 200);
        System.out.println(t.getDateString());
    }    
}
