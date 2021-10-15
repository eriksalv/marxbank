package marxbank;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import marxbank.model.User;
import javafx.scene.Node;

public class LogInController {
    @FXML private TextField typeUsername;
    @FXML private TextField typePassword;
    @FXML private Label usernameError;
    @FXML private Label passwordError;
    @FXML private Button registerButton;
    @FXML private Button logInButton;
    @FXML private Pane newPane;
    @FXML private Parent root;


    public LogInController() {
        // DataManager.manager().setPath("../data");
        // try {
        //     DataManager.manager().parse();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    @FXML
    private void handleLogInButton(MouseEvent e) throws IOException{

        usernameError.setText("");
        passwordError.setText("");

        String username = typeUsername.getText();

        if(username == null || username.equals("") || username.trim().equals("")) {
            usernameError.setText("Username cannot be blank");
            return;
        }

        if(!username.trim().equals(username)) {
            usernameError.setText("Username cannot contain spaces");
            return;
        }

        User u = DataManager.manager().getUserByUsername(username);

        if(u == null) {
            usernameError.setText("Username is wrong");
            return;
        }

        String password = typePassword.getText();

        if(password == null || password.equals("") || password.trim().equals("")) {
            passwordError.setText("Password cannot be empty");
            return;
        }

        if(!u.getPassword().equals(password)) {
            passwordError.setText("Password is wrong");
            return;
        }

        DataManager.manager().setLoggedInUser(u);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        //Access the controller and call a method
        MainController controller = loader.getController();
        controller.initData();
        
        //Get stage information
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void handleRegisterButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Register.fxml"));
        AnchorPane pane = loader.load();

        ((AnchorPane) root).getChildren().setAll(pane);
    }
}