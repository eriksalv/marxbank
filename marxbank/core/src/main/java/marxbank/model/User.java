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
  @Id
  @GeneratedValue
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
   * 
   * @param id of user
   * @param username of user
   * @param email of user
   * @param password of user
   */
  public User(Long id, String username, String email, String password) {
    validateId(id);
    this.id = id;
    validateUsername(username);
    this.username = username;
    validateEmail(email);
    this.email = email;
    validatePassword(password);
    this.password = password;
  }

  public User(String username, String email, String password) {
    this(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, username, email, password);
  }

  public void setId(Long newId) {
    this.id = validateId(newId);
  }

  private Long validateId(Long id) {
    if (id == null)
      throw new IllegalArgumentException("Id cannot be null");

    return id;
  }

  public Long getId() {
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
    this.password = validatePassword(newPassword);
  }

  public String getPassword() {
    return this.password;
  }

  public void setAccounts(ArrayList<Account> newAccountsList) {
    if (newAccountsList == null) {
      throw new IllegalArgumentException("new account list cannot be null");
    }
    this.accounts = new ArrayList<>(newAccountsList);
  }

  public ArrayList<Account> getAccounts() {
    return new ArrayList<>(this.accounts);
  }

  public Account getAccountById(Long id) {
    return getAccounts().stream().filter(acc -> acc.getId().equals(id)).findFirst().orElseThrow(
        () -> new IllegalStateException("Could not find account with given id: " + id));
  }

  public void addAccount(Account newAccount) {
    if (newAccount == null) {
      throw new IllegalArgumentException("new account cannot be null");
    }
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

  private String validateUsername(String username) {
    if (username == null)
      throw new IllegalArgumentException("Username cannot be null");
    else if (username.length() < 4)
      throw new IllegalArgumentException("Username is too short, must be 4 characters minimum.");
    else if (username.length() > 30)
      throw new IllegalArgumentException("Username is too long, must be 30 characters maximum.");
    else if (!username.trim().equals(username))
      throw new IllegalArgumentException("Username cannot start or end with a space.");
    else if (username.contains(" "))
      throw new IllegalArgumentException("Username cannot contain any spaces");

    return username;
  }

  private String validateEmail(String email) {
    if (email == null) {
      throw new IllegalArgumentException("Email cannot be null");
    } else if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/="
        .concat("?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}"))) {
      throw new IllegalArgumentException("Email is not valid");
    }
    return email;
  }

  private String validatePassword(String password) {
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    } else if (password.length() < 4) {
      throw new IllegalArgumentException("Password must be at least 4 characters long");
    }
    return password;
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
    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;
    User user = (User) o;
    return Objects.equals(id, user.getId());
  }

}
