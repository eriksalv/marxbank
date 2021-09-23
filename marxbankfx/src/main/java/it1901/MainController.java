package it1901;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {

    private User user;

    private DataManager dm = new DataManager("../data");

    @FXML private AnchorPane main;
    @FXML private Pane content;
    @FXML private VBox menuContainer;
    @FXML private Button menuBtn1;
    @FXML private Button menuBtn2;
    @FXML private Button menuBtn3;
    @FXML private Button menuBtn4;
    @FXML private Button menuBtn5;
    
    @FXML
    private void initialize() throws IOException {
        setSizeScaling();
        initData();
        handleHome();
    }

    private void setSizeScaling() {
        menuContainer.prefHeightProperty().bind(main.heightProperty());

        List<Button> menuBtns = Arrays.asList(menuBtn1, menuBtn2, menuBtn3, menuBtn4, menuBtn5);
        menuBtns.forEach(btn -> {
            btn.prefWidthProperty().bind(menuContainer.widthProperty());
            btn.prefHeightProperty().bind(menuContainer.heightProperty());
        });
    }

    private void initData() {
        user = new User("id", "username", "email@email.com", "password", this.dm);
        Account a1 = new SavingsAccount("id1", user, 5, this.dm);
        Account a2 = new SavingsAccount("id2", user, 7, this.dm);
        a1.deposit(666);
        a2.deposit(1337);

    }

    @FXML
    private void handleHome() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Home.fxml"));
        AnchorPane pane = loader.load();
        HomeController homeController = loader.getController();
        homeController.initData(user, dm);

        content.getChildren().setAll(pane);
    }

    @FXML
    private void handleMyAccounts(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyAccounts.fxml"));
        VBox pane = loader.load();
        MyAccountsController controller = loader.getController();
        controller.initData(user, dm);

        content.getChildren().setAll(pane);
    }

    @FXML 
    private void handleTransaction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Transaction.fxml"));
        AnchorPane pane = loader.load();
        TransactionController controller = loader.getController();
        controller.initData(user, dm);

        content.getChildren().setAll(pane);
    }

    @FXML
    private void handleMyTransactions(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyTransactions.fxml"));
        Pane pane = loader.load();
        MyTransactionsController controller = loader.getController();
        controller.initData(user);

        content.getChildren().setAll(pane);
    }
    
    @FXML
    private void handleMyProfile(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile.fxml"));
        AnchorPane pane = loader.load();
        ProfileController controller = loader.getController();
        controller.initData(user);

        content.getChildren().setAll(pane);
    }
}
