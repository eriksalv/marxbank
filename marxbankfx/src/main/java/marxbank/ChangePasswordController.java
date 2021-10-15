package marxbank;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import marxbank.model.User;

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
        if (!currentPasswordField.getText().equals(user.getPassword()) && !currentPasswordField.getText().equals("")) {
            saveButton.setText("Feil passord");
            return;
        }
        if((newPasswordField.getText().equals("") || confirmNewPasswordField.getText().equals("")) && !currentPasswordField.getText().equals("")) {
            saveButton.setText("Passord kan ikke være tomt");
            return;
        }
        if (newPasswordField.getText().equals(user.getPassword()) || confirmNewPasswordField.getText().equals(user.getPassword())){
            saveButton.setText("Ikke et nytt passord");
            return;
        }
        if (!(confirmNewPasswordField.getText().equals(newPasswordField.getText()))) {
            saveButton.setText("Passordene stemmer ikke");
            return;
        }

        user.setPassword(newPasswordField.getText());
        saveButton.setText("Oppdatert");
        controller.updatePassword();    
        try {
            DataManager.manager().save();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;
    
    }

    public void handleClose() {
        controller.closeChangePassword();
    }

}
