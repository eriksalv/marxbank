package it1901.wrappers;

import java.util.List;

import it1901.model.User;
import it1901.model.Account;
import it1901.model.Transaction;

public class DataManagerWrapper {
    
    public List<User> users;
    public List<Account> accounts;
    public List<Transaction> transactions;

    public DataManagerWrapper(List<User> u, List<Account> a, List<Transaction> t) {
        this.users = u;
        this.accounts = a;
        this.transactions = t;
    }



}
