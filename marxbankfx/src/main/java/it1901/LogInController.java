package it1901;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LogInController {
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField typeUsername;
    @FXML private TextField typePassword;
    @FXML private Button registerButton;
    @FXML private Button logInButton;
    @FXML private Pane newPane;

    private User user;
    private DataManager dm;

    @FXML
    private void intitialize(){

    }

    @FXML
    private void handleLogInButton(){
        this.user = dm.getUser(user.getId());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Home.fxml"));
        AnchorPane pane = loader.load();
        HomeController controller = loader.getController();
        controller.initData(user);

        newPane.getChildren().setAll(pane);

    }

}