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
    private DataManager dm;

    public void initData(User user, ProfileController c, DataManager dm) {
        this.controller = c;
        this.user = user;
        this.dm = dm;
    }

    @FXML
    public void handleSave(){
        if (!currentPasswordField.getText().equals(user.getPassword())) {
            saveButton.setText("Feil passord");
            return;
        }
        if (!newPasswordField.getText().equals(confirmNewPasswordField.getText())) {
            saveButton.setText("Passordene stemmer ikke");
            return;
        }
        user.setPassword(newPasswordField.getText());
        saveButton.setText("Oppdatert");
        controller.updatePassword();
        try {
            dm.save();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void handleClose() {
        controller.closeChangePassword();
    }

}
