package marxbank.API;

import marxbank.model.Account;
import marxbank.util.AccountType;

/**
 * En klasse som inneholder elementene i en konto som må vises til brukeren i brukergrensesnittet.
 */
public class AccountResponse {
    
    private Long id;
    private int accountNumber;
    private String type;
    private Long userId;
    private String name;
    //private List<Transaction> transactions;
    private double balance;
    private double interestRate;
/**
 * Tom konstruktør nødvendig for at SpringBoot skal fungere ordentlig.
 */
    public AccountResponse() {
    
    }

/**
 * Konstruktør som setter informasjonen i this lik den i kontoen som blir tatt inn som input.
 * @param account - en eksisterende konto
 */
    public AccountResponse(Account account){
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.type = account.getType().getTypeString();
        this.userId = account.getUser().getId();
        this.name = account.getName();
        //this.transactions = account.getTransactions();
        this.balance = account.getBalance();
        this.interestRate = account.getInterestRate();
    }

/**
 * Setters og getters.
 */
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
        this.type = newType.getTypeString();
    }
    public String getType(){
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
    public void setInterestRate(double newInterestRate){
        this.interestRate = newInterestRate;
    }
    public double getInterestRate(){
        return this.interestRate;
    }



}
