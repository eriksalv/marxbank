package it1901;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegisterController {

    @FXML private TextField usernameText;
    @FXML private TextField emailText;
    @FXML private TextField password1Text;
    @FXML private TextField password2Text;
    @FXML private AnchorPane register;
    
    
    private DataManager dm;

    public RegisterController() {
        this.dm = new DataManager("../data");
        try {
            dm.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() throws IOException {
        if (!password1Text.getText().equals(password2Text.getText())) {
            System.err.println("passwords dont match");
            return;
        }
        if (dm.checkIfUsernameIsTaken(usernameText.getText()))  {
            System.err.println("Username is already taken");
            return;
        }
        try {
            User user = new User(usernameText.getText(), emailText.getText(), password1Text.getText(), dm);
            Account initialAccount = new CheckingAccount(user, dm, "Min brukskonto");
            initialAccount.deposit(200);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
        try {
            dm.save();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LogIn.fxml"));
            AnchorPane pane = loader.load();
            ((AnchorPane) register).getChildren().setAll(pane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
