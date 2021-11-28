package marxbank.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Token {

  // token expiration time in milliseconds
  public static final int EXPIRES_IN = 900000;

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String token;

  protected Token() {}

  public Token(User user, String token) {
    if (user == null || token == null)
      throw new IllegalArgumentException();
    this.user = user;
    this.token = token;
  }

  public void setId(Long id) {
    if (id == null)
      throw new IllegalArgumentException();
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public void setToken(String token) {
    if (token == null || token.isEmpty() || token.isBlank())
      throw new IllegalArgumentException();
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  public void setUser(User user) {
    if (user == null)
      throw new IllegalArgumentException();
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Token))
      return false;
    Token t = (Token) obj;
    return Objects.equals(id, t.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
