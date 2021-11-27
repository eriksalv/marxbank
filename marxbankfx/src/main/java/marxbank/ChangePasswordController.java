package marxbank;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import marxbank.model.User;

public class ChangePasswordController {

  @FXML
  private Label changePasswordLabel;
  @FXML
  private Label currentPasswordLabel;
  @FXML
  private PasswordField currentPasswordField;
  @FXML
  private Label newPasswordLabel;
  @FXML
  private PasswordField newPasswordField;
  @FXML
  private Label confirmNewPasswordLabel;
  @FXML
  private PasswordField confirmNewPasswordField;
  @FXML
  private Button saveButton;
  @FXML
  private Button closeButton;
  @FXML
  private Label errorMsg;

  private User user;
  private ProfileController controller;

  public void initData(User user, ProfileController c) {
    this.controller = c;
    this.user = user;
  }

  @FXML
  public void handleSave() {
    if (!currentPasswordField.getText().equals(user.getPassword())
        && !currentPasswordField.getText().equals("")) {
      errorMsg.setText("Feil passord");
      return;
    }
    if ((newPasswordField.getText().equals("") || confirmNewPasswordField.getText().equals(""))
        && !currentPasswordField.getText().equals("")) {
      errorMsg.setText("Passord kan ikke være tomt");
      return;
    }
    if (newPasswordField.getText().equals(user.getPassword())
        || confirmNewPasswordField.getText().equals(user.getPassword())) {
      errorMsg.setText("Ikke et nytt passord");
      return;
    }
    if (!(confirmNewPasswordField.getText().equals(newPasswordField.getText()))) {
      errorMsg.setText("Passordene stemmer ikke");
      return;
    }

    try {
      user.setPassword(newPasswordField.getText());
      errorMsg.setText("Oppdatert");
    } catch (IllegalArgumentException e) {
      errorMsg.setText(e.getMessage());
    }
    try {
      DataManager.save();
    } catch (Exception e) {
      errorMsg.setText("Noe gikk galt med lagring");
    }
    return;

  }

  public void handleClose() {
    controller.closeChangePassword();
  }

}
