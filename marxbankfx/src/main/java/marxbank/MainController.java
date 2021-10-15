package marxbank;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import marxbank.model.User;

public class MainController {

    private User user;

    private String currentContent = "Home"; //used for testing

    // public MainController(User user, DataManager dm) {
    //     this.user=user;
    //     this.dm=dm;
    // }

    @FXML private AnchorPane main;
    @FXML private Pane content;
    @FXML private VBox menuContainer;
    @FXML private Button menuBtn1;
    @FXML private Button menuBtn2;
    @FXML private Button menuBtn3;
    @FXML private Button menuBtn4;
    @FXML private Button menuBtn5;
    @FXML private Button savingsCalcMenuBtn;
    
    @FXML
    private void initialize() {
        setSizeScaling();
    }

    public String getCurrentContent() {
        return this.currentContent;
    }

    private void setSizeScaling() {
        menuContainer.prefHeightProperty().bind(main.heightProperty());

        List<Button> menuBtns = Arrays.asList(menuBtn1, menuBtn2, menuBtn3, menuBtn4, menuBtn5, savingsCalcMenuBtn);
        menuBtns.forEach(btn -> {
            btn.prefWidthProperty().bind(menuContainer.widthProperty());
            btn.prefHeightProperty().bind(menuContainer.heightProperty());
        });
    }

    public void initData() throws IOException {
        this.initData(DataManager.manager().getLoggedInUser());
    }

    public void initData(User user) throws IOException {
        if (user==null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        this.user = user;
        if (this.user.getAccounts().size()>0) {
            handleHome();
        }
    }

    @FXML
    private void handleHome() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Home.fxml"));
        AnchorPane pane = loader.load();
        HomeController homeController = loader.getController();
        homeController.initData(user);

        content.getChildren().setAll(pane);
        currentContent="Home";
    }

    @FXML
    private void handleMyAccounts() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyAccounts.fxml"));
        VBox pane = loader.load();
        MyAccountsController controller = loader.getController();
        controller.initData(user);

        content.getChildren().setAll(pane);
        currentContent="MyAccounts";
    }

    @FXML 
    private void handleTransaction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Transaction.fxml"));
        AnchorPane pane = loader.load();
        TransactionController controller = loader.getController();
        controller.initData(user);

        content.getChildren().setAll(pane);
        currentContent="Transaction";
    }

    @FXML
    private void handleMyTransactions() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyTransactions.fxml"));
        Pane pane = loader.load();
        MyTransactionsController controller = loader.getController();
        controller.initData(user);

        content.getChildren().setAll(pane);
        currentContent="MyTransactions";
    }

    @FXML
    private void handleSavingsCalc() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SavingsCalc.fxml"));
        AnchorPane pane = loader.load();

        content.getChildren().setAll(pane);
        currentContent="SavingsCalc";
    }
    
    @FXML
    private void handleMyProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile.fxml"));
        AnchorPane pane = loader.load();
        ProfileController controller = loader.getController();
        controller.initData(user);

        content.getChildren().setAll(pane);
        currentContent="MyProfile";
    }
}
