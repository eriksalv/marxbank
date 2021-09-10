package it1901;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Transaction {
    
    private Account from;
    private Account reciever;
    private double amount;
    private Date transactionDate;
    private String dateString;

    //formaterer dato til lesbar norsk tekststreng automatisk
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss", new Locale("nb")); 

    public Transaction(Account from, Account reciever, double amount) {
        this.from=from;
        this.reciever=reciever;
        this.amount=amount;
        transactionDate=new Date();
        dateString=dateFormat.format(transactionDate);
    }

    public String getDateString() {
        return dateString;
    }

    public void commitTransaction() {
        //TODO
    }

    public static void main(String[] args) {
        Transaction t = new Transaction(new SavingsAccount(2), new SavingsAccount(2), 200);
        System.out.println(t.getDateString());
    }    
}
