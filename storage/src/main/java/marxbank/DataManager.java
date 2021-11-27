package marxbank;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;
import marxbank.util.ValidPath;

public class DataManager {

  private static String dataPath = "data";

  private static User loggedInUser;

  private static List<User> userList = new ArrayList<User>();
  private static List<Account> accountList = new ArrayList<Account>();
  private static List<Transaction> transactionList = new ArrayList<Transaction>();
  private static AtomicInteger accountCounter = new AtomicInteger(1);

  private DataManager() {}

  /**
   * Sets path for DataManager to use the standard storage directory should not be used.
   * 
   * @param path to storage directory
   */
  public static void setPath(String path) {
    if (!ValidPath.isValidPath(path))
      throw new IllegalArgumentException("Not a valid path.");
    dataPath = path;
  }

  /**
   * Creates a new User and returns it
   * 
   * @param username of User
   * @param email of User
   * @param password of User
   * @return the created User
   * @throws IllegalArguementException if username is already taken
   */
  public static User createUser(String username, String email, String password) {
    if (checkIfUsernameIsTaken(username))
      throw new IllegalArgumentException("Username is already taken");
    User u = new User(username, email, password);
    addUser(u);
    return u;
  }

  /**
   * Creates a new Account and returns it
   * 
   * @param type of account
   * @param user owner of account
   * @param name of account
   * @return account that has been created
   */
  public static Account createAccount(String type, User user, String name) {
    Account a = AccountFactory.create(type, user, name);
    if (a == null) {
      throw new IllegalArgumentException("No account type provided");
    }
    addAccount(a);
    return a;
  }

  /**
   * Creates a new transaction and returns it
   * 
   * @param from account transaction is sent from
   * @param reciever account transaction is sent to
   * @param amount of money
   * @return transaction that has been created
   */
  public static Transaction createTransaction(Account from, Account reciever, double amount) {
    Transaction t = new Transaction(from, reciever, amount);
    addTransaction(t);
    return t;
  }

  /**
   * Adds user to userlist
   * 
   * @param u user to add
   * @throws IllegalArgumentException user already exists in userList
   */
  public static void addUser(User u) {
    if (userList.contains(u))
      throw new IllegalArgumentException("User already in userList");
    userList.add(u);
  }

  /**
   * Delets user from userList
   * 
   * @param u user to delete
   * @throws IllegalArgumentException if user does not exist in userList
   */
  public static void deleteUser(User u) {
    if (!userList.contains(u))
      throw new IllegalArgumentException("User doesn't exist");
    userList.remove(u);
  }

  /**
   * Adds account to accountList
   * 
   * @param a account to add
   * @throws IllegalArgumentException if account already exists in accountList
   */
  public static void addAccount(Account a) {
    if (accountList.contains(a))
      throw new IllegalArgumentException("Account already in accountList");
    if (a.getId().equals(0l)) {
      a.setId((long) accountCounter.get());
      a.setAccountNumber(a.generateAccountNumber());
    }
    accountCounter.getAndIncrement();
    accountList.add(a);
  }

  /**
   * deletes account from accountList
   * 
   * @param a account to delete
   * @throws IllegalArgumentException if account does not exist in accountList
   */
  public static void deleteAccount(Account a) {
    if (!accountList.contains(a))
      throw new IllegalArgumentException("Account doesn't exist");
    accountList.remove(a);
    for (User u : userList) {
      if (u.getAccounts().contains(a)) {
        u.getAccounts().remove(a);
      }
    }
  }

  /**
   * Adds transaction to transactionList
   * 
   * @param t transaction to add
   * @throws IllegalArgumentException if transaction already exists in transactionList
   */
  public static void addTransaction(Transaction t) {
    if (transactionList.contains(t))
      throw new IllegalArgumentException("Transaction already in transactionList");
    transactionList.add(t);
  }

  /**
   * Deletes transaction from transactionList
   * 
   * @param t transaction to delete
   * @throws IllegalArgumentException if transaction does not exist in transactionList
   */
  public static void deleteTransaction(Transaction t) {
    if (!transactionList.contains(t))
      throw new IllegalArgumentException("Transaction doesn't exist");
    transactionList.remove(t);
    for (Account a : accountList) {
      if (a.getTransactions().contains(t)) {
        a.getTransactions().remove(t);
      }
    }
  }

  public static List<User> getUsers() {
    return userList;
  }

  public static List<Account> getAccounts() {
    return accountList;
  }

  public static List<Transaction> getTransactions() {
    return transactionList;
  }

  /**
   * Checks if a users password is already taken.
   * 
   * @param username user to check for
   * @return true if found, false otherwise
   */
  public static boolean checkIfUsernameIsTaken(String username) {
    return userList.stream().anyMatch(user -> user.getUsername().equals(username));
  }

  /**
   * checks if a User exists in userList given its id
   * 
   * @param id of user
   * @return true if user is found, false otherwise
   */
  public static boolean checkIfUserExists(long id) {
    try {
      userList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  /**
   * checks if Account object exists in accountList
   * 
   * @param a Account to check for
   * @return true if account is found, false otherwise
   */
  public static boolean checkIfAccountExists(Account a) {
    return accountList.contains(a);
  }

  /**
   * checks if Account object exists in accountList given its id
   * 
   * @param id of account
   * @return true if found, false otherwise
   */
  public static boolean checkIfAccountExists(long id) {
    try {
      accountList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  /**
   * checks if Transaction object exists in transactionList
   * 
   * @param t transaction
   * @return true if found, false otherwise
   */
  public static boolean checkIfTransactionExists(Transaction t) {
    return transactionList.contains(t);
  }

  /**
   * Checks if Transaction object exists given its id
   * 
   * @param id of transaction
   * @return true if found, false otherwise
   */
  public static boolean checkIfTransactionExists(long id) {
    try {
      transactionList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  /**
   * gets logged in user
   * 
   * @return user that is logged in
   */
  public static User getLoggedInUser() {
    return loggedInUser;
  }

  /**
   * set logged in user
   * 
   * @param u to be logged in
   */
  public static void setLoggedInUser(User u) {
    loggedInUser = u;
  }

  /**
   * Finds user by its username
   * 
   * @param username of user
   * @return User if found, otherwise null
   */
  public static User getUserByUsername(String username) {
    try {
      return userList.stream().filter(u -> u.getUsername().equals(username)).findFirst().get();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  /**
   * Gets User object given its id
   * 
   * @param id of User object
   * @return User object if found, null otherwise
   */
  public static User getUser(long id) {
    if (checkIfUserExists(id))
      return userList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    return null;
  }

  /**
   * Gets Account object given its id
   * 
   * @param id of Account object
   * @return Account object if found, null otherwise
   */
  public static Account getAccount(long id) {
    if (checkIfAccountExists(id))
      return accountList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    return null;
  }

  public static Account getAccount(int accountNumber) {
    return getAccounts().stream().filter(acc -> acc.getAccountNumber() == accountNumber).findFirst().orElseThrow(
        () -> new IllegalStateException("Could not find account with given account number: " + accountNumber));
  }

  /**
   * Gets Transaction object given its id
   * 
   * @param id of Transaction Object
   * @return Transaction object if found, null otherwise
   */
  public static Transaction getTransaction(long id) {
    if (checkIfTransactionExists(id))
      return transactionList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    return null;
  }

  /**
   * Gets index of given User
   * 
   * @param user user
   * @return index of User if found, -1 otherwise
   */
  public static int getIndexOfUser(User user) {
    return userList.indexOf(user);
  }

  /**
   * Gets index of given Account
   * 
   * @param account account
   * @return index of Account if found, -1 otherwise
   */
  public static int getIndexOfAccount(Account account) {
    return accountList.indexOf(account);
  }

  /**
   * Gets index of given Transaction
   * 
   * @param transaction transaction
   * @return index of Transaction if found, -1 otherwise
   */
  public static int getIndexOfTransaction(Transaction transaction) {
    return transactionList.indexOf(transaction);
  }

  /**
   * Saves all data in the DataManager to local storage
   * 
   * @throws IllegalStateException if it cannot save data
   */
  public static void save() {
    boolean saved = DataHandler.save(new ArrayList<User>(userList), new ArrayList<Account>(accountList), new ArrayList<Transaction>(transactionList), dataPath);
    if (!saved)
      throw new IllegalStateException();
  }

  /**
   * Reads all locally saved data to DataManager
   * 
   * @throws IllegalStateException if it cannot read data
   */
  public static void parse() {
    boolean parsed = DataHandler.parse(dataPath);
    if (!parsed)
      throw new IllegalStateException();
  }

  /**
   * Used to clear all data in the DataManager, used mostly for testing
   */
  public static void resetData() {
    userList = new ArrayList<User>();
    accountList = new ArrayList<Account>();
    transactionList = new ArrayList<Transaction>();
    accountCounter.set(1);
  }
}
