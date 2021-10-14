package it1901.wrappers;

import java.util.List;

import it1901.model.User;
import it1901.DataManager;
import it1901.model.Account;
import it1901.model.Transaction;

public class DataManagerWrapper {
    
    public List<User> users;
    public List<Account> accounts;
    public List<Transaction> transactions;

    public DataManagerWrapper(DataManager dm) {
        this.users = dm.getUsers();
        this.accounts = dm.getAccounts();
        this.transactions = dm.getTransactions();
    }



}
