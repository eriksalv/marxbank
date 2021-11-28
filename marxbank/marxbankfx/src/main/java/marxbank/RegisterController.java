package marxbank;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import marxbank.model.Account;
import marxbank.model.User;
import marxbank.util.Loader;

public class RegisterController {

  @FXML
  private TextField usernameText;
  @FXML
  private TextField emailText;
  @FXML
  private TextField password1Text;
  @FXML
  private TextField password2Text;
  @FXML
  private AnchorPane register;
  @FXML
  private Button registerBtn;
  @FXML
  private Label registerFailedMsg;
  @FXML
  private Button cancelBtn;
  @FXML
  private Parent root;

  @FXML
  private void initialize() {
    registerFailedMsg.setVisible(false);
    registerFailedMsg.setText("");
    setBtnDisableProperty();
  }

  private void setBtnDisableProperty() {
    BooleanBinding emptyText =
        Bindings.isEmpty(usernameText.textProperty()).or(Bindings.isEmpty(emailText.textProperty()))
            .or(Bindings.isEmpty(password1Text.textProperty()))
            .or(Bindings.isEmpty(password2Text.textProperty()));
    registerBtn.disableProperty().bind(emptyText);
  }

  @FXML
  private void handleRegister() throws IOException {
    if (!password1Text.getText().equals(password2Text.getText())) {
      System.err.println("passwords dont match");
      registerFailedMsg.setVisible(true);
      registerFailedMsg.setText("Passwords dont match");
      return;
    }
    if (DataManager.checkIfUsernameIsTaken(usernameText.getText())) {
      System.err.println("Username is already taken");
      registerFailedMsg.setVisible(true);
      registerFailedMsg.setText("Username is already taken");
      return;
    }
    try {
      User user = DataManager.createUser(usernameText.getText(), emailText.getText(),
          password1Text.getText());
      Account initialAccount = DataManager.createAccount("Brukskonto", user, "Min brukskonto");

      initialAccount.deposit(200);

      try {
        DataManager.save();
      } catch (IllegalStateException e) {
        e.printStackTrace();
      }

    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      registerFailedMsg.setVisible(true);
      registerFailedMsg.setText(e.getMessage());
      return;
    }
    try {
      DataManager.save();
      FXMLLoader loader = Loader.loadFxml(getClass(), "LogIn.fxml");
      AnchorPane pane = loader.load();
      register.getChildren().setAll(pane);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleCancel(ActionEvent e) throws IOException {
    DataManager.setLoggedInUser(null);

    Loader.changeScene(getClass(), "LogIn.fxml", e);
  }

}
