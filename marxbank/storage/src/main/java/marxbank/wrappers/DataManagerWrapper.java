package marxbank.wrappers;

import java.util.ArrayList;

import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;

public class DataManagerWrapper {
    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Account> accountList = new ArrayList<Account>();
    private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    public DataManagerWrapper(ArrayList<User> userList, ArrayList<Account> accountList,
                                 ArrayList<Transaction> transactionList) {
        this.userList = userList;
        this.accountList = accountList;
        this.transactionList = transactionList;
    }

    public ArrayList<User> getUsers() {
        return this.userList;
    }

    public ArrayList<Account> getAccounts() {
        return this.accountList;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactionList;
    }
}
