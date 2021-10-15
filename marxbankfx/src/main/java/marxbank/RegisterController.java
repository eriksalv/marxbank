package marxbank;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.scene.Scene;
import javafx.stage.Stage;
import marxbank.model.Account;
import marxbank.model.User;
import javafx.scene.Node;

public class RegisterController {

    @FXML private TextField usernameText;
    @FXML private TextField emailText;
    @FXML private TextField password1Text;
    @FXML private TextField password2Text;
    @FXML private AnchorPane register;
    @FXML private Button registerBtn;
    @FXML private Label registerFailedMsg;
    @FXML private Button cancelBtn;
    
    public RegisterController() {
        // DataManager.manager().setPath("../data");
        // try {
        //     DataManager.manager().parse();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    @FXML
    private void initialize() {
        registerFailedMsg.setVisible(false);
        registerFailedMsg.setText("");
    }

    @FXML
    private void handleRegister() throws IOException {
        if (!password1Text.getText().equals(password2Text.getText())) {
            System.err.println("passwords dont match");
            registerFailedMsg.setVisible(true);
            registerFailedMsg.setText("Passwords dont match");
            return;
        }
        if (DataManager.manager().checkIfUsernameIsTaken(usernameText.getText()))  {
            System.err.println("Username is already taken");
            registerFailedMsg.setVisible(true);
            registerFailedMsg.setText("Username is already taken");
            return;
        }
        try {
            User user = DataManager.manager().createUser(usernameText.getText(), emailText.getText(), password1Text.getText());
            Account initialAccount = DataManager.manager().createAccount("Brukskonto", user, "Min brukskonto");

            initialAccount.deposit(200);

            try {
                DataManager.manager().save();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            registerFailedMsg.setVisible(true);
            registerFailedMsg.setText(e.getMessage());
            return;
        }
        try {
            DataManager.manager().save();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LogIn.fxml"));
            AnchorPane pane = loader.load();
            register.getChildren().setAll(pane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent e) throws IOException {
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
