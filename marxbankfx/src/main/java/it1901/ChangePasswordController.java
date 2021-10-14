package it1901;

import it1901.model.User;
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
        if (!currentPasswordField.getText().equals(user.getPassword())) {
            saveButton.setText("Feil passord");
            return;
        }
        if (!(newPasswordField.getText().equals(user.getPassword()))){
            user.setPassword(newPasswordField.getText());
            saveButton.setText("Oppdatert");
            controller.updatePassword();
            try {
                DataManager.manager().save();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else {
            saveButton.setText("Ugyldig passord");
        }
    }

    public void handleClose() {
        controller.closeChangePassword();
    }

}
