package marxbank.API;

import java.util.List;

import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;
import marxbank.util.AccountFactory;
import marxbank.util.AccountType;

public class AccountRequest {

    private Long id;
    private int accountNumber;
    private String type;
    private User user;
    private String name;
    private List<Transaction> transactions;
    private double balance;
    private double interestRate;

    protected AccountRequest() {}

    public AccountRequest(Long id, int accountNumber, String type, String name, double balance, double interestRate){
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        //this.user = user;
        this.name = name;
        //this.transactions = transactions;
        this.balance = balance;
        this.interestRate = interestRate;
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
    public void setType(String newType){
        this.type = newType;
    }
    public String getType(){
        return this.type;
    }
    public void setUser(User newUser){
        this.user = newUser;
    }
    public User getUser(){
        return this.user;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public String getName(){
        return this.name;
    }
    public void setTransactions(List<Transaction> newTransactions){
        this.transactions = newTransactions;
    }
    public List<Transaction> getTransactions(){
        return this.transactions;
    }
    public void setBalance(double newBalance){
        this.balance = newBalance;
    }
    public double getBalance(){
        return this.balance;
    }
    public void setInterestRate(double newInterestRate){
        this.interestRate = newInterestRate;
    }
    public double getInterestRate(){
        return this.interestRate;
    }
    
    public Account createAccount() {
        return AccountFactory.createFrom(type, id, user, name, accountNumber);
    }
}
