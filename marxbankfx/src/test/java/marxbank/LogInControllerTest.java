package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import marxbank.model.User;

public class LogInControllerTest extends ApplicationTest {

  private User user;
  private Scene s;
  private Stage st;

  @Override
  public void start(final Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
    Parent root = loader.load();
    s = new Scene(root);
    this.st = stage;
    stage.setScene(s);
    stage.show();
  }

  @BeforeEach
  void setup() throws Exception {
    DataManager.resetData();
    user = DataManager.createUser("testUser", "test@email.com", "password");
    DataManager.createAccount("Sparekonto", user, "nicu");
  }

  @Test
  @DisplayName("test logIn Username Space")
  public void testLogInUsernameSpace() {
    clickOn("#typeUsername").write(" testUser ");
    clickOn("#logInButton");
    assertEquals("Username cannot contain spaces",
        ((Label) lookup("#usernameError").query()).getText());
  }

  @Test
  @DisplayName("test logIn with wrong username")
  public void testLogInUsernameWrong() {
    clickOn("#typeUsername").write("yeetser");
    clickOn("#logInButton");
    assertEquals("Username is wrong", ((Label) lookup("#usernameError").query()).getText());
  }

  @Test
  @DisplayName("test logIn with empty Username")
  public void testLogInEmptyUsername() {
    clickOn("#typeUsername").write("");
    clickOn("#logInButton");
    assertEquals("Username cannot be blank", ((Label) lookup("#usernameError").query()).getText());

    clickOn("#typeUsername").write(" ");
    clickOn("#logInButton");
    assertEquals("Username cannot be blank", ((Label) lookup("#usernameError").query()).getText());
  }

  @Test
  @DisplayName("test logIn with wrong password")
  public void testWrongPassword() {
    clickOn("#typeUsername").write("testUser");
    clickOn("#typePassword").write("superWrongPassword");
    clickOn("#logInButton");
    assertEquals("", ((Label) lookup("#usernameError").query()).getText());
    assertEquals("Password is wrong", ((Label) lookup("#passwordError").query()).getText());
  }

  @Test
  @DisplayName("test logIn with empty password")
  public void testEmptyPassword() {
    clickOn("#typeUsername").write("testUser");
    clickOn("#typePassword").write("");
    clickOn("#logInButton");
    assertEquals("", ((Label) lookup("#usernameError").query()).getText());
    assertEquals("Password cannot be empty", ((Label) lookup("#passwordError").query()).getText());

    clickOn("#typePassword").write(" ");
    clickOn("#logInButton");
    assertEquals("", ((Label) lookup("#usernameError").query()).getText());
    assertEquals("Password cannot be empty", ((Label) lookup("#passwordError").query()).getText());
  }

  @Test
  @DisplayName("test successful login")
  public void testLogIn() {
    clickOn("#typeUsername").write("testUser");
    clickOn("#typePassword").write("password");
    clickOn("#logInButton");
    assertEquals(user, DataManager.getLoggedInUser());
  }

  @Test
  @DisplayName("test Click on register button")
  public void testRegisterButton() {
    clickOn("#registerButton");
    assertEquals("register", st.getScene().getRoot().getChildrenUnmodifiable().get(0).getId());
  }

}
