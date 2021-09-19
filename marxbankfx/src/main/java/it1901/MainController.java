package it1901;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {

    private User user;

    @FXML private Pane content;
    
    @FXML
    private void initialize() {
        initData();
    }

    private void initData() {
        user = new User();
        Account a1 = new SavingsAccount(user, 3);
        Account a2 = new SavingsAccount(user, 5);
        a1.deposit(666);
        a2.deposit(1337);
        user.addAccount(a1);
        user.addAccount(a2);
    }

    @FXML
    private void handleHome(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Home.fxml"));
        AnchorPane pane = loader.load();

        content.getChildren().setAll(pane);
    }

    @FXML
    private void handleMyAccounts(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyAccounts.fxml"));
        VBox pane = loader.load();
        MyAccountsController controller = loader.getController();
        controller.initData(user);

        content.getChildren().setAll(pane);
    }
}
