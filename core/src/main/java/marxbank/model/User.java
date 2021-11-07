package marxbank.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
    @Id @GeneratedValue
    private Long id; 
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Account> accounts = new ArrayList<Account>();
    @OneToOne(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Token token;

    public User() {}

    /**
     * Constructor for user with arguments
     * @param id of user
     * @param username of user
     * @param email of user
     * @param password of user
     */
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = validateUsername(username);
        this.email = validateEmail(email);
        this.password = password; // going to get hashed later
    }

    public User(String username, String email, String password) {
       this(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, username, email, password);
    }

    public User(String username, String email, String password, boolean generateId) {
        this.username = validateUsername(username);
        this.email = validateEmail(email);
        this.password = password;
    }

    public void setId(Long newId) {
        if (newId == null) throw new IllegalArgumentException("Id cannot be null");
        this.id = newId;
    }

    public Long getId() {
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

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return this.token;
    }

    private String validateUsername(String username) throws IllegalArgumentException {
        if (username.length() < 4) throw new IllegalArgumentException("username is too short, must be 4 characters minimum.");
        if (username.length() > 30) throw new IllegalArgumentException("username is too long, must be 30 characters maximum.");
        if (!username.trim().equals(username)) throw new IllegalArgumentException("Username cannot start or end with a space.");
        if (username.contains(" ")) throw new IllegalArgumentException("Username cannot contain any spaces");

        return username;
    }

    private boolean validateUsernameBool(String username) {
        if (username.length() < 4) return false;
        if (username.length() > 30) return false;
        if (!username.trim().equals(username)) return false;
        if (username.contains(" ")) return false;

        return true;
    }

    private String validateEmail(String email) {
        if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) throw new IllegalArgumentException("Email is not valid");
        return email;
    }

    private boolean validateEmailBool(String email) {
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}");
    }


    public boolean validate() {
        if (!validateUsernameBool(this.username) || !validateEmailBool(this.email)) return false;
        return true;
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