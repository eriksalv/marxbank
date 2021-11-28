package marxbank;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import marxbank.model.User;
import marxbank.util.Loader;

public class LogInController {
  @FXML
  private TextField typeUsername;
  @FXML
  private PasswordField typePassword;
  @FXML
  private Label usernameError;
  @FXML
  private Label passwordError;
  @FXML
  private Button registerButton;
  @FXML
  private Button logInButton;
  @FXML
  private Pane newPane;
  @FXML
  private Parent root;

  private AnchorPane register;

  @FXML
  private void initialize() {
    try {
      FXMLLoader loader = Loader.loadFxml(getClass(), "Register.fxml");
      register = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleLogInButton(MouseEvent e) throws IOException {

    usernameError.setText("");
    passwordError.setText("");

    String username = typeUsername.getText();

    if (username == null || username.equals("") || username.trim().equals("")) {
      usernameError.setText("Username cannot be blank");
      return;
    }

    if (!username.trim().equals(username)) {
      usernameError.setText("Username cannot contain spaces");
      return;
    }

    User u = DataManager.getUserByUsername(username);

    if (u == null) {
      usernameError.setText("Username is wrong");
      return;
    }

    String password = typePassword.getText();

    if (password == null || password.equals("") || password.trim().equals("")) {
      passwordError.setText("Password cannot be empty");
      return;
    }

    if (!u.getPassword().equals(password)) {
      passwordError.setText("Password is wrong");
      return;
    }

    DataManager.setLoggedInUser(u);

    Loader.changeScene(getClass(), "Main.fxml", e);
  }

  @FXML
  private void handleRegisterButton() throws IOException {
    ((AnchorPane) root).getChildren().setAll(register);
  }
}
