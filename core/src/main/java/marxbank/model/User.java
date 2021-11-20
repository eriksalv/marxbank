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
        setUsername(username);
        setEmail(email);
        setPassword(password);
    }

    public User(String username, String email, String password) {
       this(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, username, email, password);
    }

    public void setId(Long newId) {
        if (newId == null) throw new IllegalArgumentException("Id cannot be null");
        this.id = newId;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String newUsername) {
        validateUsername(newUsername, true);
        this.username = newUsername;
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String newEmail) {
        validateEmail(newEmail, true);
        this.email = newEmail;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String newPassword) {
        validatePassword(newPassword, true);
        this.password = newPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setAccounts(ArrayList<Account> newAccountsList) {
        if (newAccountsList == null) {
            throw new IllegalArgumentException("new account list cannot be null");
        }
        this.accounts = newAccountsList;
    }

    public ArrayList<Account> getAccounts() {
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

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return this.token;
    }

    private boolean validateUsername(String username, boolean throwException) throws IllegalArgumentException {
        if (throwException) {
            if (username.length() < 4) throw new IllegalArgumentException("username is too short, must be 4 characters minimum.");
            else if (username.length() > 30) throw new IllegalArgumentException("username is too long, must be 30 characters maximum.");
            else if (!username.trim().equals(username)) throw new IllegalArgumentException("Username cannot start or end with a space.");
            else if (username.contains(" ")) throw new IllegalArgumentException("Username cannot contain any spaces");
        }

        return !(username.length() < 4 && username.length() > 30 && !username.trim().equals(username) && username.contains(" "));
    }

    private boolean validateEmail(String email, boolean throwException) {
        if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) {
            if (throwException) {
                throw new IllegalArgumentException("Email is not valid");
            }
            return false;
        } 
        return true;
    }

    private boolean validatePassword(String password, boolean throwException) {
        if (password == null) {
            if (throwException) {
                throw new IllegalArgumentException("Password cannot be null");
            }
            return false;
        }
        else if (password.length() < 4) {
            if (throwException) {
                throw new IllegalArgumentException("Password must be at least 4 characters long");
            }
            return false;
        }
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