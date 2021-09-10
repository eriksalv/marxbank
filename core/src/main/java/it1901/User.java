package it1901;
import java.util.ArrayList;

class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private ArrayList<IAccount> accounts = new ArrayList<IAccount>();


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
    public void setAccounts(ArrayList<IAccount> newAccountsList) {
        this.accounts = newAccountsList;
    }
    public ArrayList<IAccount> getAccounts() {
        return this.accounts;
    }

    public void addAccount(IAccount newAccount) {
        if (!accounts.contains(newAccount)) {
            accounts.add(newAccount);
        } else {
            throw new IllegalArgumentException("This account is already added.");
        }
    }
    public void removeAccount(IAccount unwantedAccount) {
        if (accounts.contains(unwantedAccount)) {
            accounts.remove(unwantedAccount);
        } else {
            throw new IllegalArgumentException("This account does not exist in your accounts.");
        }
    }

}