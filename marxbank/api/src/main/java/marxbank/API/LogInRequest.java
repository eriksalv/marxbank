package marxbank.API;

/**
 * Klassen holder på informasjon er bruker gi når de ønsker å logge inn på sin profil på nettsiden
 * til banken.
 */

public class LogInRequest {

  private String username;
  private String password;

  /**
   * Tom konstruktør.
   */
  protected LogInRequest() {}

  /**
   * Konstruktør for klassen.
   * 
   * @param username - brukernavn
   * @param password - passord
   */
  public LogInRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Setters og getters.
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

  @Override
  public String toString() {
    return "username: " + username;
  }

}
