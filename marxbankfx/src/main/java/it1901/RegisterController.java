package it1901;

import java.io.IOException;

import it1901.model.Account;
import it1901.model.CheckingAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegisterController {

    @FXML private TextField usernameText;
    @FXML private TextField emailText;
    @FXML private TextField password1Text;
    @FXML private TextField password2Text;
    @FXML private AnchorPane register;
    @FXML private Button registerBtn;
    @FXML private Label registerFailedMsg;
    
    
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
    private void initialize() {
        registerFailedMsg.setVisible(false);
        registerFailedMsg.setText("");
    }

    public DataManager getDM() {
        return this.dm;
    }

    protected void setDM(DataManager dm) {
        if (dm!=null) {
            this.dm=dm;
        } else {
            throw new IllegalArgumentException("dm cannot be null");
        }
    }

    @FXML
    private void handleRegister() throws IOException {
        if (!password1Text.getText().equals(password2Text.getText())) {
            System.err.println("passwords dont match");
            registerFailedMsg.setVisible(true);
            registerFailedMsg.setText("Passwords dont match");
            return;
        }
        if (dm.checkIfUsernameIsTaken(usernameText.getText()))  {
            System.err.println("Username is already taken");
            registerFailedMsg.setVisible(true);
            registerFailedMsg.setText("Username is already taken");
            return;
        }
        try {
            User user = new User(usernameText.getText(), emailText.getText(), password1Text.getText(), dm);
            Account initialAccount = new CheckingAccount(user, dm, "Min brukskonto");
            initialAccount.deposit(200);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            registerFailedMsg.setVisible(true);
            registerFailedMsg.setText(e.getMessage());
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
