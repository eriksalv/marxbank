package marxbank.API;

import java.time.LocalDateTime;

import marxbank.model.Account;
import marxbank.model.Transaction;

public class TransactionResponse {
    
    private Long Id;
    private Account from;
    private Account reciever;
    private double amount;
    private LocalDateTime transactionDate;

    public TransactionResponse(){
    }
    
    public TransactionResponse(Transaction transaction){
        this.Id = transaction.getId();
        this.from = transaction.getFrom();
        this.reciever = transaction.getReciever();
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
    }

    public void setId(Long newId){
        this.Id=newId;
    }
    public Long getId(){
        return this.Id;
    }
    public void setFrom(Account fromAccount){
        this.from=fromAccount;
    }
    public Account getFrom(){
        return this.from;
    }
    public void setReciever(Account receivingAccount){
        this.reciever = receivingAccount;
    }
    public Account getReciever(){
        return this.reciever;
    }
    public void setAmount(double newAmount){
        this.amount = newAmount;
    }
    public double getAmount(){
        return this.amount;
    }
    public void setTransactionDate(LocalDateTime newDate){
        this.transactionDate = newDate;
    }
    public LocalDateTime getTransDateTime(){
        return this.transactionDate;
    }

}
