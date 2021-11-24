package marxbank.API;

public class LogInResponse {

  private String token;
  private UserResponse userResponse;

  public LogInResponse(String token, UserResponse userResponse) {
    this.token = token;
    this.userResponse = userResponse;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  public void setUserRespone(UserResponse u) {
    this.userResponse = u;
  }

  public UserResponse getUserResponse() {
    return this.userResponse;
  }
}
