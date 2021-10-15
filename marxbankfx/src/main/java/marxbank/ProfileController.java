package marxbank;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import marxbank.model.User;
import javafx.scene.Node;

public class ProfileController {
    
    @FXML private Label nameLabel;
    @FXML private Label IDLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button changePasswordButton;
    @FXML private Pane newPane;

    private User user;

    public void initData(User u) {
        this.user = u;
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
        controller.initData(user, this);

        newPane.getChildren().setAll(pane);
    }

    public void updatePassword() {
        passwordLabel.setText(user.getPassword());
    }

    public void closeChangePassword() {
        newPane.getChildren().clear();
    }

    @FXML
    private void handleSignOut(ActionEvent e) throws IOException {
        DataManager.manager().setLoggedInUser(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LogIn.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        //Get stage information
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
}
