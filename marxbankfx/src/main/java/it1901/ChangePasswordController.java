package it1901;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChangePasswordController {
    
    @FXML private Label changePasswordLabel;
    @FXML private Label currentPasswordLabel;
    @FXML private TextField currentPasswordField;
    @FXML private Label newPasswordLabel;
    @FXML private TextField newPasswordField;
    @FXML private Label confirmNewPasswordLabel;
    @FXML private TextField confirmNewPasswordField;
    @FXML private Button saveButton;
    @FXML private Button closeButton;

    private User user;
    private ProfileController controller;

    public void initData(User user, ProfileController c) {
        this.controller = c;
        this.user = user;
    }

    @FXML
    public void handleSave(){
        if (!(newPasswordField.getText().equals(user.getPassword()))){

        user.setPassword(newPasswordField.getText());
        saveButton.setText("Oppdatert");
        controller.updatePassword();
        }
        else {
            saveButton.setText("Ugyldig passord");
            throw new IllegalArgumentException("You cannot change into a password that is already used.");
        }
    }

    public void handleClose() {
        controller.closeChangePassword();
    }

}
