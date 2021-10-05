package it1901;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LogInController {
    @FXML private TextField typeUsername;
    @FXML private TextField typePassword;
    @FXML private Label usernameError;
    @FXML private Label passwordError;
    @FXML private Button registerButton;
    @FXML private Button logInButton;
    @FXML private Pane newPane;
    @FXML private Parent root;

    private DataManager dm;

    public LogInController() {
        this.dm = new DataManager("../data");
        try {
            dm.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void intitialize(){
    }

    @FXML
    private void handleLogInButton() throws IOException{

        usernameError.setText("");
        passwordError.setText("");

        String username = typeUsername.getText();

        if(!username.trim().equals(username)) {
            usernameError.setText("Username cannot contain spaces");
            return;
        }

        User u = dm.getUserByUsername(username);

        if(u == null) {
            usernameError.setText("Username is wrong");
            return;
        }

        String password = typePassword.getText();

        if(!u.getPassword().equals(password)) {
            passwordError.setText("Password is wrong");
            return;
        }

        this.dm.setLoggedInUser(u);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main.fxml"));
        AnchorPane pane = loader.load();
        MainController controller = loader.getController();
        controller.initData(dm);

        ((AnchorPane) root).getChildren().setAll(pane);

    }

    @FXML
    private void handleRegisterButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Register.fxml"));
        AnchorPane pane = loader.load();

        ((AnchorPane) root).getChildren().setAll(pane);
    }

    // only used for testing purposes
    public DataManager getDM() {
        return this.dm;
    }
}