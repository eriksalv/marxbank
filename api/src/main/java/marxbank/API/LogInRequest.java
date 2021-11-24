package marxbank.API;

public class LogInRequest {

  private String username;
  private String password;

  protected LogInRequest() {}

  public LogInRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

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
