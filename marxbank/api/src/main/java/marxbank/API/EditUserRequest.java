package marxbank.API;

/**
 * Klassen holder på informasjon en bruker sender inn om de ønsker å endre på informasjonen koblet
 * til profilen deres.
 */
public class EditUserRequest {
  private String username;
  private String password;
  private String oldPassword;
  private String email;

  /**
   * Tom konstruktør.
   */
  protected EditUserRequest() {}

  /**
   * Konstruktør som oppdaterer this-objektet med ny informasjon.
   * 
   * @param username - brukernavn
   * @param password - nytt passord
   * @param oldPassword - gammelt passord(som bruker ønsker å bytte ut)
   * @param email - epostadresse
   */
  public EditUserRequest(String username, String password, String oldPassword, String email) {
    this.username = username;
    this.password = password;
    this.oldPassword = oldPassword;
    this.email = email;
  }

  /**
   * Getters og setters.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return this.password;
  }

  public void setOldPassword(String password) {
    this.oldPassword = password;
  }

  public String getOldPassword() {
    return this.oldPassword;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }

  @Override
  public String toString() {
    return "username: " + username;
  }
}
