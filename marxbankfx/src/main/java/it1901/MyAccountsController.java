package it1901;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MyAccountsController {

    private User user;
    private Account account;
    private DataManager dm;
    
    @FXML private VBox myAccounts;
    @FXML private VBox accountBtns;

    @FXML
    private void initialize() {
    }

    public void initData(User user, DataManager dm) {
        this.user = user;
        this.dm = dm;
        createAccountButtons();
    }

    /**
     * Creates a new button for every account on the users account-list
     */
    private void createAccountButtons() {
        List<Account> accounts = user.getAccounts();
        for (Account a : accounts) {
            Button accountBtn = new Button();
            accountBtn.getStyleClass().add("accBtn");
            accountBtn.setText("Kontonummer: " + a.getAccountNumber());
            accountBtn.setOnAction(ev -> {
                account = a;
                try {
                    handleSelectAccount(ev);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            accountBtns.getChildren().add(accountBtn);
        }
    }

    /**
     * Replaces all nodes in myAccounts VBox with the content of Account.fxml, and "transfers" the
     * selected account to AccountController
     * @param e
     * @throws IOException
     */
    @FXML
    private void handleSelectAccount(ActionEvent e) throws IOException {       
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Account.fxml"));
        AnchorPane pane = loader.load();
        AccountController controller = loader.getController();
        controller.initData(account);

        myAccounts.getChildren().setAll(pane);
    }

    @FXML
    private void handleCreateNewAccount(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("createNewAccount.fxml"));
        createNewAccountController controller = new createNewAccountController(user, dm);
        loader.setController(controller);
        AnchorPane pane = loader.load();

        myAccounts.getChildren().setAll(pane);
    }

}
