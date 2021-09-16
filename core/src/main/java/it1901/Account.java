package it1901;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Account {
    
    enum Type {
        SAVING
    }

    @JsonBackReference
    private User user;
    private String id;
    @JsonManagedReference
    private List<Transaction> transactions = new LinkedList<Transaction>();
    private double balance = 0;
    private double interestRate; //I prosent
    private Type type;

    public Account(String id, User user, double interestRate, Type type) {
        this.user = user;
        this.interestRate = interestRate;
        this.id = id;
        this.type = type;
    }

    public void deposit(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Deposit must be positive");
		}
		balance+=amount;
    }

    public void withdraw(double amount) {
        if (amount<=0) {
			throw new IllegalArgumentException("Must withdraw a positive amount");
		} else if (balance-amount<0) {
			throw new IllegalStateException("Not enough balance on account");
		}
		balance-=amount;
    }

    public void addInterest() {
        deposit(getBalance()*getInterestRate()/100);
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public void setInterestRate(double i) {
        this.interestRate = i;
    }

    public void setTransactions(List<Transaction> t) {
        this.transactions = t;
    }

    public void addTransaction(Transaction t) {
        if (this.transactions.contains(t)) {
            throw new IllegalStateException("Transaction is already registered");
        }
        this.transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return new LinkedList<>(this.transactions);
    }

    /**
     * saves an account to local storage
     * 
     * @param account to save
     * @throws IllegalArgumentException if acount is null
     * @throws IllegalArgumentException if accounts fields user and id is null
     */
    public static void saveAccount(Account account) {
        if(account == null) throw new IllegalArgumentException("account cannot be null");
        if(account.getUser() == null || account.getId() == null) throw new IllegalArgumentException("accounts user cannot be null");

        ObjectMapper objectMapper = new ObjectMapper();

        File accountFile = new File(String.format("..data/accounts/%s.%s.json", account.getId(), account.getUser().getUsername()));

        try {
            objectMapper.writeValue(accountFile, account);
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) {
                try {
                    accountFile = new File(String.format("data/accoutns/%s.%s.json", account.getId(), account.getUser().getUsername()));
                    objectMapper.writeValue(accountFile, account);
                } catch (Exception a) {
                    System.out.println(String.format("Error: %s", a));
                }
            } else {
                System.out.println(String.format("Error: %s", e));
            }
        }
    }

    /**
     * saves account object to local storage
     * @throws IllegalStateException if account object has fields that are null
     */
    public void saveAccount() {
        if(this.id == null || this.user == null) throw new IllegalStateException("user and id cannot be null.");
        ObjectMapper objectMapper = new ObjectMapper();

        File accountFile = new File(String.format("../data/accounts/%s.%s.json", this.id, this.user.getUsername()));
        try {
            objectMapper.writeValue(accountFile, this);
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                try {
                    accountFile = new File(String.format("data/accounts/%s.%s.json", this.id, this.user.getUsername()));
                    objectMapper.writeValue(accountFile, this);
                } catch (Exception a) {
                    System.out.println(String.format("Error: %s", a));
                }
            } else {
                System.out.println(String.format("Error: %s", e));
            }
        }
    }

    /**
     * Reads an account from a given path to a jsonfile
     * @param path to account
     * @return the account if found and null if not found
     * @throws FileNotFoundException if accountfile was not found
     * @throws IllegalArgumentException if path is not a valid apth
     */
    public static Account readAccount(String path) throws FileNotFoundException {
        // check if path is valid
        if(!ValidPath.isValidPath(path)) throw new IllegalArgumentException("");
        // check if file exists
        if(!new File(path).exists()) throw new FileNotFoundException("No File with that path exists.");

        ObjectMapper objectMapper = new ObjectMapper();
        Account account;

        File accountFile = new File(path);

        // read account as correct type
        try {
            account = objectMapper.readValue(accountFile, Account.class);
        } catch (Exception e) {
            account = null;
        }

        return account;

    }

    /**
     * reads account file to this account object
     * @param path to account file
     * @throws IllegalArgumentException if path is not valid
     * @throws FileNotFoundException if file was not found
     * @throws IllegalStateException if there was an error reading the account correctly
     */
    public void readAccountToAccount(String path) throws FileNotFoundException {
        // check if path is valid
        if(!ValidPath.isValidPath(path)) throw new IllegalArgumentException("Path is not valid.");
        // check if file exists
        if(!new File(path).exists()) throw new FileNotFoundException("File was not found");

        ObjectMapper objectMapper = new ObjectMapper();
        Account account = null;
        File accountFile = new File(path);

        try {
            JsonNode node = objectMapper.readTree(accountFile);
            Type type = Type.valueOf(node.get("Type").asText());
            if(type == Type.SAVING) {
                account = objectMapper.readValue(accountFile, SavingsAccount.class);
            }
        } catch (Exception e) {
            System.out.println(String.format("Errors: %s", e));
        }

        if(account == null) throw new IllegalStateException("");

        this.setId(account.getId());
        this.setUser(account.getUser());
        this.setBalance(account.getBalance());
        this.setInterestRate(account.getInterestRate());
        this.setTransactions(account.getTransactions());
    }

    

    public static void main(String... args) {
        User u = new User("1", "username", "email@email.com", "password");
        Account a = new SavingsAccount("1", u, 5.0);
        a.saveAccount();
    }
}
