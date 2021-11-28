package marxbank;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import marxbank.model.User;
import marxbank.util.Loader;

public class ProfileController {

  @FXML
  private Label nameLabel;
  @FXML
  private Label idLabel;
  @FXML
  private Label usernameLabel;
  @FXML
  private Button changePasswordButton;
  @FXML
  private Pane newPane;

  private User user;

  private AnchorPane changePassword;
  private ChangePasswordController changePasswordController;

  @FXML
  private void initialize() {
    loadViews();
  }

  public void initData(User u) {
    this.user = u;
    nameLabel.setText(user.getEmail());
    idLabel.setText(user.getId().toString());
    usernameLabel.setText(user.getUsername());
  }

  private void loadViews() {
    try {
      FXMLLoader changePasswordLoader = Loader.loadFxml(getClass(), "ChangePassword.fxml");
      this.changePassword = changePasswordLoader.load();
      this.changePasswordController = changePasswordLoader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleChangePassword(ActionEvent e) throws IOException {
    changePasswordController.reset();
    changePasswordController.initData(user, this);
    newPane.getChildren().setAll(changePassword);
  }

  public void closeChangePassword() {
    newPane.getChildren().clear();
  }

  @FXML
  private void handleSignOut(ActionEvent e) throws IOException {
    DataManager.setLoggedInUser(null);

    Loader.changeScene(getClass(), "LogIn.fxml", e);
  }
}
