package marxbank.API;

import java.util.List;

import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.util.AccountType;

public class AccountResponse {
    
    private Long id;
    private int accountNumber;
    private AccountType type;
    private Long userId;
    private String name;
    //private List<Transaction> transactions;
    private double balance;
    //private double interestRate;

    public AccountResponse() {
    
    }

    public AccountResponse(Account account){
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.type = account.getType();
        this.userId = account.getUser().getId();
        this.name = account.getName();
        //this.transactions = account.getTransactions();
        this.balance = account.getBalance();
        //this.interestRate = account.getInterestRate();
    }

    public void setId(Long newId){
        this.id = newId;
    }
    public Long getId(){
        return this.id;
    }
    public void setAccountNumber(int newAccountNumber){
        this.accountNumber = newAccountNumber;
    }
    public int getAccountNumber(){
        return this.accountNumber;
    }
    public void setType(AccountType newType){
        this.type = newType;
    }
    public AccountType getType(){
        return this.type;
    }
    public void setUserId(Long userId){
        this.userId = userId;
    }
    public Long getUser(){
        return this.userId;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public String getName(){
        return this.name;
    }
    // public void setTransactions(List<Transaction> newTransactions){
    //     this.transactions = newTransactions;
    // }
    // public List<Transaction> getTransactions(){
    //     return this.transactions;
    // }
    public void setBalance(double newBalance){
        this.balance = newBalance;
    }
    public double getBalance(){
        return this.balance;
    }
    // public void setInterestRate(double newInterestRate){
    //     this.interestRate = newInterestRate;
    // }
    // public double getInterestRate(){
    //     return this.interestRate;
    // }



}
