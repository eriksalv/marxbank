package marxbank.API;

import marxbank.model.User;


/**
 * Klassen inneholder det brukeren trenger å vite om sin egen profil.
 */
public class UserResponse {

  private long id;
  private String username;
  private String email;

  public UserResponse() {}

  public UserResponse(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
  }

  public UserResponse(long id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public long getId() {
    return this.id;
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
}
