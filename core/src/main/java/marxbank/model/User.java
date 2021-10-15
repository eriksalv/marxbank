package marxbank.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private List<Account> accounts = new ArrayList<Account>();

    /**
     * Constructor for user with arguments
     * @param id of user
     * @param username of user
     * @param email of user
     * @param password of user
     */
    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = validateUsername(username);
        this.email = validateEmail(email);
        this.password = password; // going to get hashed later
    }

    public User(String username, String email, String password) {
        this(UUID.randomUUID().toString(), username, email, password);
    }

    public void setId(String newId) {
        if (newId == null) throw new IllegalArgumentException("Id cannot be null");
        this.id = newId;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String newUsername) {
        this.username = validateUsername(newUsername);
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String newEmail) {
        this.email = validateEmail(newEmail);
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

    public ArrayList<Account> getAccounts() {
        return (ArrayList<Account>) this.accounts;
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

   private String validateUsername(String username) {
        if (username.length() < 4) throw new IllegalArgumentException("username is too short, must be 4 characters minimum.");
        if (username.length() > 30) throw new IllegalArgumentException("username is too long, must be 30 characters maximum.");
        if (!username.trim().equals(username)) throw new IllegalArgumentException("Username cannot start or end with a space.");
        if (username.contains(" ")) throw new IllegalArgumentException("Username cannot contain any spaces");

        return username;
   }

   private String validateEmail(String email) {
        if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) throw new IllegalArgumentException("Email is not valid");
        return email;
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