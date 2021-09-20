package it1901;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import it1901.util.AccountDeserializer;
import it1901.util.TransactionDeserializer;
import it1901.util.UserDeserializer;
import it1901.util.ValidPath;

public class DataManager {
    
    private String path;

    private List<User> userList = new ArrayList<User>();
    private List<Account> accountList = new ArrayList<Account>();
    private List<Transaction> transactionList = new ArrayList<Transaction>();

    /**
     * DataManager is a class that handles all of the data managing of the app
     * It handles: saving, parsing and upkeep of all objects in app
     * @param path to storage location for data
     */
    public DataManager(String path) {
        if(!ValidPath.isValidPath(path)) throw new IllegalArgumentException("Path is not valid");
        if(!new File(path).exists()) throw new IllegalArgumentException("Storage Directory does not exist");
        if(!new File(path).isDirectory()) throw new IllegalArgumentException("Path is not a directory");
        this.path = path;
    }

    /**
     * Adds user to userlist
     * @param u user to add
     * @throws IllegalArgumentException is user already exists in userList
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
     * checks if a User object exists in userlist
     * @param u User to check for
     * @return true if found, false otherwise
     */
    public boolean checkIfUserExists(User u) {
        if(this.userList.contains(u)) return true;
        return false;
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
     * Updates User with new parameters
     * @param id of User
     * @param user User object
     */
    public void updateUser(String id, User user) {
        User u = getUser(id);
        int index = getIndexOfUser(u);
        this.userList.set(index, user);
    }

    /**
     * Updates Account with new parameters
     * @param id of Account
     * @param account Account object
     */
    public void updateAccount(String id, Account account) {
        Account a = getAccount(id);
        int index = getIndexOfAccount(a);
        this.accountList.set(index, account);
    }

    /**
     * Updates Transaction with new parameteres
     * @param id of Transaction
     * @param transaction Transaction object
     */
    public void updateTransaction(String id, Transaction transaction) {
        Transaction t = getTransaction(id);
        int index = getIndexOfTransaction(t);
        this.transactionList.set(index, transaction);
    }

    /**
     * Parses Users, Accounts and Transactions from storage
     * @throws Exception if it cannot read or create storage files
     */
    public void parse() throws Exception {
        // parse users
        File userFile = new File(String.format("%s/users.json", this.path));
        parseUsers(userFile);

        // parse accounts
        File accountDir = new File(String.format("%s/accounts.json", this.path));
        parseAccounts(accountDir);

        //parse transactions
        File transactionDir = new File(String.format("%s/transactions.json", this.path));
        parseTransactions(transactionDir);
    }

    private void parseUsers(File userFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // add user deserialzer
        SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new UserDeserializer(this));
        objectMapper.registerModule(module);
        // check for user dir in data directory
        // if directory doesn't exist, make a new one
        if(!userFile.exists()){
            try {
                userFile.createNewFile();
                // exit because file has just been created
                return;
            } catch (IOException e) {
                throw new Exception("Cannot create users.json");
            }
        }

        JsonNode masterUserNode;

        try {
            masterUserNode = objectMapper.readTree(userFile);
        } catch (IOException e) {
            throw new Exception("Cannot create users.json");
        }

        // parse users
        for(JsonNode e : masterUserNode.get("users")) {
            User u = null;
            try {
                u = objectMapper.treeToValue(e, User.class);
                // check if user exists already
                if(checkIfUserExists(u.getId())) {
                    // if user is found check if its already been added, if so continue
                    if(checkIfUserExists(u)) continue;
                    // otherwise update old account
                    User u2 = getUser(u.getId());
                    if(u2.getUsername() == u.getUsername() && u2.getEmail() == u.getEmail()) {
                        updateUser(u2.getId(), u);
                    }
                } else {
                    addUser(u);
                }
            } catch (JsonProcessingException e1) {
                continue;
            }
        }
    }

    private void parseAccounts(File accountFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // add account deserialzer
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Account.class, new AccountDeserializer(this));
        objectMapper.registerModule(module);

        if(!accountFile.exists()) {
            try {
                accountFile.createNewFile();
                return;
            } catch (IOException e) {
                throw new Exception("Cannot create accounts.json");
            }
        }

        JsonNode masterNode;

        try {
            masterNode = objectMapper.readTree(accountFile);
        } catch (IOException e) {
            throw new Exception("Cannot read users.json");
        }

        if(this.userList.size() == 0) return;

        for(JsonNode e : masterNode.get("accounts")) {
            Account account = null;
            try {
                account = objectMapper.treeToValue(e, Account.class);
                if(checkIfAccountExists(account.getId())) {
                    Account a2 = getAccount(account.getId());
                    if(a2.getUser() == account.getUser()) continue;
                    updateAccount(a2.getId(), account);
                } else {
                    addAccount(account);
                }
            } catch (JsonProcessingException e1) {
                continue;
            }

        }
    }

    private void parseTransactions(File transactionFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // add transaction deserialzer
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Transaction.class, new TransactionDeserializer(this));
        objectMapper.registerModule(module);

        if(!transactionFile.exists()) {
            try {
                transactionFile.createNewFile();
                return;
            } catch (IOException e) {
                throw new Exception("Cannot create transactions.json");
            }
        }

        JsonNode masterTransactionNode;
        
        try {
            masterTransactionNode = objectMapper.readTree(transactionFile);
        } catch (IOException e) {
            throw new Exception("cannot read transactions.json");
        }

        if(this.accountList.size() == 0) return;

        for(JsonNode e : masterTransactionNode.get("transactions")) {
            Transaction transaction = null;
            try {
                transaction = objectMapper.treeToValue(e, Transaction.class);
                if(checkIfTransactionExists(transaction.getId())) {
                    Transaction t2 = getTransaction(transaction.getId());
                    if(t2.getFrom() == transaction.getFrom() && t2.getReciever() == transaction.getReciever() && t2.getTransactionDate() == transaction.getTransactionDate()) continue;
                    updateTransaction(t2.getId(), transaction);
                } else {
                    addTransaction(transaction);
                }
            } catch (JsonProcessingException e1) {
                continue;
            }
        }
    }

    /**
     * Saves data to storage
     * @throws Exception if it cannot write to or create storage files
     */
    public void save() throws Exception {
        File userFile = new File(String.format("%s/users.json", this.path));
        saveUsers(userFile);

        File accountFile = new File(String.format("%s/accounts.json", this.path));
        saveAccounts(accountFile);

        File transactionFile = new File(String.format("%s/transactions.json", this.path));
        saveTransactions(transactionFile);
    }

    private void saveUsers(File userFile) throws Exception {
        FileWriter userFileWriter;
        ObjectMapper objectMapper = new ObjectMapper();

        if(!userFile.exists()) {
            try {
                if(userFile.createNewFile()) {
                    userFileWriter = new FileWriter(userFile);
                    userFileWriter.write(String.format("{\"users\":%s}", objectMapper.writeValueAsString(this.userList)));
                    userFileWriter.close();
                } else {
                    throw new Exception("cannot create users.json file");
                }
            } catch (IOException e) {
                throw new Exception("Cannot create users.json file");
            }
        } else {
            try {
                userFileWriter = new FileWriter(userFile);
                userFileWriter.write(String.format("{\"users\":%s}", objectMapper.writeValueAsString(this.userList)));
                userFileWriter.close();
            } catch (IOException e) {
                throw new Exception("Cannot write to users.json");
            }
        }
    }

    private void saveAccounts(File accountFile) throws Exception {
        FileWriter accountFileWriter;
        ObjectMapper objectMapper = new ObjectMapper();

        if(!accountFile.exists()) {
            try {
                if(accountFile.createNewFile()) {
                    accountFileWriter = new FileWriter(accountFile);
                    accountFileWriter.write(String.format("{\"accounts\":%s}", objectMapper.writeValueAsString(this.accountList)));
                    accountFileWriter.close();
                } else {
                    throw new Exception("cannot create accounts.json file");
                }
            } catch (IOException e) {
                throw new Exception("Cannot create accounts.json file");
            }
        } else {
            try {
                accountFileWriter = new FileWriter(accountFile);
                accountFileWriter.write(String.format("{\"accounts\":%s}", objectMapper.writeValueAsString(this.accountList)));
                accountFileWriter.close();
            } catch (IOException e) {
                throw new Exception("Cannot write to accounts.json");
            }
        }
    }

    private void saveTransactions(File transactionFile) throws Exception {
        FileWriter transactionFileWriter;
        ObjectMapper objectMapper = new ObjectMapper();

        if(!transactionFile.exists()) {
            try {
                if(transactionFile.createNewFile()) {
                    transactionFileWriter = new FileWriter(transactionFile);
                    transactionFileWriter.write(String.format("{\"transactions\":%s}", objectMapper.writeValueAsString(this.transactionList)));
                    transactionFileWriter.close();
                } else {
                    throw new Exception("Cannot create transactions.json file");
                }
            } catch (IOException e) {
                throw new Exception("Cannot create transactions.json file");
            }
        } else {
            try {
                transactionFileWriter = new FileWriter(transactionFile);
                transactionFileWriter.write(String.format("{\"transactions\":%s}", objectMapper.writeValueAsString(this.transactionList)));
                transactionFileWriter.close();
            } catch(IOException e) {
                throw new Exception("Cannot write to transactions.json file");
            }
        }
    }
}
