package it1901;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ProfileController {
    
    @FXML private Label nameLabel;
    @FXML private Label IDLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button changePasswordButton;
    @FXML private Pane newPane;

    private User user;

    @FXML
    private void intitialize(){
        nameLabel.setText(user.getEmail());
        IDLabel.setText(user.getId());
        usernameLabel.setText(user.getUsername());
        passwordLabel.setText(user.getPassword());

    }
    @FXML
    private void handleChangePassword(ActionEvent e) throws IOException {       
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChangePassword.fxml"));
        AnchorPane pane = loader.load();
        ChangePasswordController controller = loader.getController();
        controller.initData(user);

        newPane.getChildren().setAll(pane);
    }


}
