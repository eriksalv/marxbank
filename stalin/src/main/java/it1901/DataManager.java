package it1901;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import it1901.model.User;
import it1901.model.Account;
import it1901.model.Transaction;
import it1901.util.ValidPath;

public class DataManager {
    
    private static DataManager dataInstance = null;

    private String path;

    private User loggedInUser;

    private List<User> userList = new ArrayList<User>();
    private List<Account> accountList = new ArrayList<Account>();
    private List<Transaction> transactionList = new ArrayList<Transaction>();

    private DataManager() {}

    public DataManager manager() {
        if(dataInstance == null) {
            synchronized (DataManager.class) {
                if(dataInstance == null) {
                    dataInstance = new DataManager();
                }
            }
        }

        return dataInstance;
    }

    public void setPath(String path) {
        if(!ValidPath.isValidPath(path)) throw new IllegalArgumentException("Not a valid path.");
        this.path = path;
    }

    /**
     * Adds user to userlist
     * @param u user to add
     * @throws IllegalArgumentException user already exists in userList
     */
    public void addUser(User u) {
        if(this.userList.contains(u)) throw new IllegalArgumentException("User already in userList");
        userList.add(u);
    }

    /**
     * Delets user from userList
     * @param u user to delete
     * @throws IllegalArgumentException if user does not exist in userList
     */
    public void deleteUser(User u) {
        if(!this.userList.contains(u)) throw new IllegalArgumentException("User doesn't exist");
        userList.remove(u);
    }

    /**
     * Adds account to accountList
     * @param a account to add
     * @throws IllegalArgumentException if account already exists in accountList
     */
    public void addAccount(Account a) {
        if(this.accountList.contains(a)) throw new IllegalArgumentException("Account already in accountList");
        accountList.add(a);
    }

    /**
     * deletes account from accountList
     * @param a account to delete
     * @throws IllegalArgumentException if account does not exist in accountList
     */
    public void deleteAccount(Account a) {
        if(!this.accountList.contains(a)) throw new IllegalArgumentException("Account doesn't exist");
        accountList.remove(a);
    }

    /**
     * Adds transaction to transactionList
     * @param t transaction to add
     * @throws IllegalArgumentException if transaction already exists in transactionList
     */
    public void addTransaction(Transaction t) {
        if(this.transactionList.contains(t)) throw new IllegalArgumentException("Transaction already in transactionList");
        transactionList.add(t);
    }

    /**
     * Deletes transaction from transactionList
     * @param t transaction to delete
     * @throws IllegalArgumentException if transaction does not exist in transactionList
     */
    public void deleteTransaction(Transaction t) {
        if(!this.transactionList.contains(t)) throw new IllegalArgumentException("Transaction doesn't exist");
        transactionList.remove(t);
    }

    public List<User> getUsers() {
        return this.userList;
    }

    public List<Account> getAccounts() {
        return this.accountList;
    }

    public List<Transaction> getTransactions() {
        return this.transactionList;
    }

    /**
     * Checks if a users password is already taken
     * @param u user to check for
     * @return true if found, false otherwise
     */
    public boolean checkIfUsernameIsTaken(String username) {
        return this.userList.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    /**
     * checks if a User exists in userList given its id
     * @param id of user
     * @return true if user is found, false otherwise
     */
    public boolean checkIfUserExists(String id) {
        try {
            this.userList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * checks if Account object exists in accountList
     * @param a Account to check for
     * @return true if account is found, false otherwise
     */
    public boolean checkIfAccountExists(Account a) {
        if(this.accountList.contains(a)) return true;
        return false;
    }

    /**
     * checks if Account object exists in accountList given its id
     * @param id of account
     * @return true if found, false otherwise
     */
    public boolean checkIfAccountExists(String id) {
        try {
            this.accountList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * checks if Transaction object exists in transactionList
     * @param t transaction
     * @return true if found, false otherwise
     */
    public boolean checkIfTransactionExists(Transaction t) {
        if(this.transactionList.contains(t)) return true;
        return false;
    }

    /**
     * Checks if Transaction object exists given its id
     * @param id of transaction
     * @return true if found, false otherwise
     */
    public boolean checkIfTransactionExists(String id) {
        try {
            this.transactionList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * gets logged in user
     * @return user that is logged in
     */
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * set logged in user
     * @param u to be logged in
     */
    public void setLoggedInUser(User u) {
        this.loggedInUser = u;
    }

    /**
     * Gets User object given its id
     * @param id of User object
     * @return User object if found, null otherwise
     */
    public User getUser(String id) {
        if(checkIfUserExists(id)) return this.userList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
        return null;
    }

    /**
     * Gets Account object given its id
     * @param id of Account object
     * @return Account object if found, null otherwise
     */
    public Account getAccount(String id) {
        if(checkIfAccountExists(id)) return this.accountList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
        return null;
    }

    /**
     * Gets Transaction object given its id
     * @param id of Transaction Object
     * @return Transaction object if found, null otherwise
     */
    public Transaction getTransaction(String id) {
        if(checkIfTransactionExists(id)) return this.transactionList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
        return null;
    }

    /**
     * Gets index of given User
     * @param user user
     * @return index of User if found, -1 otherwise
     */
    public int getIndexOfUser(User user) {
        return this.userList.indexOf(user);
    }

    /**
     * Gets index of given Account
     * @param account account
     * @return index of Account if found, -1 otherwise
     */
    public int getIndexOfAccount(Account account) {
        return this.accountList.indexOf(account);
    }

    /**
     * Gets index of given Transaction
     * @param transaction transaction
     * @return index of Transaction if found, -1 otherwise
     */
    public int getIndexOfTransaction(Transaction transaction) {
        return this.transactionList.indexOf(transaction);
    }
    
    /**
     * Saves all data in the DataManager to local storage
     * @throws IllegalStateException if it cannot save data
     * @deprecated
     */
    public void save() {
        //if(!DataHandler.save(this, this.path)) throw new IllegalStateException();
    }

    /**
     * Reads all locally saved data to DataManager
     * @throws IllegalStateException if it cannot read data
     * @deprecated
     */
    public void parse() {
        //if(!DataHandler.parse(this, this.path)) throw new IllegalStateException();
    }

}
