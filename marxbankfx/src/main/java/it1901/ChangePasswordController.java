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

    private User user;

    @FXML
    private void intitialize(){
    }
    public void initData(User user) {
        this.user = user;
    }

    @FXML
    public void handleSave(){
        user.setPassword(newPasswordField.getText());
        saveButton.setText("Oppdatert");
    }

}
