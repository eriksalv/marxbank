package it1901;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

    private String id;
    private String username;
    private String email;
    private String password;
    @JsonIgnoreProperties({"user", "transactions", "balance", "interestRate", "type", "dm"})
    private List<Account> accounts = new ArrayList<Account>();
    @JsonIgnore
    private DataManager dm;

    /**
     * Constructor for user with arguments
     * @param id of user
     * @param username of user
     * @param email of user
     * @param password of user
     */
    public User(String id, String username, String email, String password, DataManager dm) {
        this.id = id;
        this.username = validateUsername(username);
        this.email = validateEmail(email);
        this.password = password; // going to get hashed later
        this.dm = dm;

        this.dm.addUser(this);
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
        updateUser();
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
        updateUser();
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
        updateUser();
    }

    public String getPassword() {
        return this.password;
    }

    public void setAccounts(ArrayList<Account> newAccountsList) {
        this.accounts = newAccountsList;
        updateUser();
    }

    public ArrayList<Account> getAccounts() {
        return (ArrayList<Account>) this.accounts;
    }

    public void addAccount(Account newAccount) {
        if (!accounts.contains(newAccount)) {
            accounts.add(newAccount);
            updateUser();
        } else {
            throw new IllegalArgumentException("This account is already added.");
        }
    }

    public void removeAccount(Account unwantedAccount) {
        if (accounts.contains(unwantedAccount)) {
            accounts.remove(unwantedAccount);
            updateUser();
        } else {
            throw new IllegalArgumentException("This account does not exist in your accounts.");
        }
    }

   public String saveUser() {
       try {
            return (new ObjectMapper()).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
   }

   private String validateUsername(String username) {
       if(username.length() < 4) throw new IllegalArgumentException("username is too short, must be 4 characters minimum.");
       if(username.length() > 30) throw new IllegalArgumentException("username is too long, must be 30 characters maximum.");
       if(!username.trim().equals(username)) throw new IllegalArgumentException("Username cannot start or end with a space.");
       if(username.contains(" ")) throw new IllegalArgumentException("Username cannot contain any spaces");

       return username;
   }

   private String validateEmail(String email) {
       if(!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) throw new IllegalArgumentException("Email is not valid");
       return email;
   }

   private void updateUser() {
       this.dm.updateUser(this.id, this);
   }

    @Override
    public String toString() {
        return String.format("id:%s, username:%s", this.getId(), this.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.getId());
    }

}