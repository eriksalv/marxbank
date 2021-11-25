package marxbank.API;

/**
 * Klassen inneholder elementer applikasjonen må ha for å respondere når en bruker logger inn.
 */

public class LogInResponse {

  private String token;
  private UserResponse userResponse;

/**
 * Tom konstruktør.
 */
    protected LogInResponse() {}

/**
 * Konstruktør for LogInResponse.
 * @param token - bestemmer om innloggingsforespørselen er godkjent.
 * @param userResponse - informasjon om og for en bruker.
 */
    public LogInResponse(String token, UserResponse userResponse) {
        this.token = token;
        this.userResponse = userResponse;
    }

/**
 * Setters og getters.
 */
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
