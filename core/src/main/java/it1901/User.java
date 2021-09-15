package it1901;

import java.util.ArrayList;
import java.util.List;

class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private List<Account> accounts = new ArrayList<Account>();


    public void setId(String newId) {
        this.id = newId;
    }
    public String getId() {
        return id;
    }
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
    public String getUsername() {
        return this.username;
    }
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    public String getEmail() {
        return this.email;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public String getPassword() {
        return this.password;
    }
    public void setAccounts(ArrayList<Account> newAccountsList) {
        this.accounts = newAccountsList;
    }
    public List<Account> getAccounts() {
        return new ArrayList<>(this.accounts);
    }

    public void addAccount(Account newAccount) {
        if (!accounts.contains(newAccount)) {
            accounts.add(newAccount);
        } else {
            throw new IllegalArgumentException("This account is already added.");
        }
    }
    public void removeAccount(Account unwantedAccount) {
        if (accounts.contains(unwantedAccount)) {
            accounts.remove(unwantedAccount);
        } else {
            throw new IllegalArgumentException("This account does not exist in your accounts.");
        }
    }

}