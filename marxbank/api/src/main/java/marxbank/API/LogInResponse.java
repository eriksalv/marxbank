package marxbank.API;

/**
 * Klassen inneholder elementer applikasjonen må ha for å respondere når en bruker logger inn.
 */

public class LogInResponse {

  private String token;
  private int expiresIn;
  private UserResponse userResponse;

  /**
   * Tom konstruktør.
   */
  protected LogInResponse() {}

  /**
   * Konstruktør for LogInResponse.
   * 
   * @param token - bestemmer om innloggingsforespørselen er godkjent.
   * @param userResponse - informasjon om og for en bruker.
   * @param expiresIn - tid før token går ut på dato, håndteres i frontend.
   */
  public LogInResponse(String token, int expiresIn, UserResponse userResponse) {
    this.token = token;
    this.expiresIn = expiresIn;
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

  public void setExpiresIn(int exp) {
    this.expiresIn = exp;
  }

  public int getExpiresIn() {
    return this.expiresIn;
  }

  public void setUserRespone(UserResponse u) {
    this.userResponse = u;
  }

  public UserResponse getUserResponse() {
    return this.userResponse;
  }
}
