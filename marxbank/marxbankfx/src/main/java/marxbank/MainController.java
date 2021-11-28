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
import marxbank.util.Loader;

public class MainController {

  private User user;

  private String currentContent = "Home"; // used for testing

  @FXML
  private AnchorPane main;
  @FXML
  private Pane content;
  @FXML
  private VBox menuContainer;
  @FXML
  private Button menuBtn1;
  @FXML
  private Button menuBtn2;
  @FXML
  private Button menuBtn3;
  @FXML
  private Button menuBtn4;
  @FXML
  private Button menuBtn5;
  @FXML
  private Button savingsCalcMenuBtn;

  private AnchorPane home;
  private VBox myAccounts;
  private AnchorPane transaction;
  private Pane myTransactions;
  private AnchorPane savingsCalc;
  private AnchorPane profile;
  private AnchorPane account;

  private HomeController homeController;
  private MyAccountsController myAccountsController;
  private TransactionController transactionController;
  private MyTransactionsController myTransactionsController;
  private SavingsCalcController savingsCalcController;
  private ProfileController profileController;
  private AccountController accountController;

  @FXML
  private void initialize() throws IOException {
    initData();
    // Loads all views on initialization, and never loads them again
    loadViews();
    setSizeScaling();
    handleHome();
  }

  /**
   * Loads all views accessed through the main view
   */
  private void loadViews() {
    try {
      FXMLLoader homeLoader = Loader.loadFXML(getClass(), "Home.fxml");
      this.home = homeLoader.load();
      this.homeController = homeLoader.getController();

      FXMLLoader myAccountsLoader = Loader.loadFXML(getClass(), "MyAccounts.fxml");
      this.myAccounts = myAccountsLoader.load();
      this.myAccountsController = myAccountsLoader.getController();

      FXMLLoader transactionLoader = Loader.loadFXML(getClass(), "Transaction.fxml");
      this.transaction = transactionLoader.load();
      this.transactionController = transactionLoader.getController();

      FXMLLoader myTransactionsLoader = Loader.loadFXML(getClass(), "MyTransactions.fxml");
      this.myTransactions = myTransactionsLoader.load();
      this.myTransactionsController = myTransactionsLoader.getController();

      FXMLLoader savingsCalcLoader = Loader.loadFXML(getClass(), "SavingsCalc.fxml");
      this.savingsCalc = savingsCalcLoader.load();
      this.savingsCalcController = savingsCalcLoader.getController();

      FXMLLoader profileLoader = Loader.loadFXML(getClass(), "Profile.fxml");
      this.profile = profileLoader.load();
      this.profileController = profileLoader.getController();

      FXMLLoader accountLoader = Loader.loadFXML(getClass(), "Account.fxml");
      this.account = accountLoader.load();
      this.accountController = accountLoader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getCurrentContent() {
    return this.currentContent;
  }

  private void setSizeScaling() {
    menuContainer.prefHeightProperty().bind(main.heightProperty());

    List<Button> menuBtns =
        Arrays.asList(menuBtn1, menuBtn2, menuBtn3, menuBtn4, menuBtn5, savingsCalcMenuBtn);
    menuBtns.forEach(btn -> {
      btn.prefWidthProperty().bind(menuContainer.widthProperty());
      btn.prefHeightProperty().bind(menuContainer.heightProperty());
    });
  }

  public void initData() throws IOException {
    this.initData(DataManager.getLoggedInUser());
  }

  public void initData(User user) throws IOException {
    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    this.user = user;
  }

  @FXML
  private void handleHome() throws IOException {
    content.getChildren().setAll(home);
    homeController.initData(user, content, account, accountController);
    currentContent = "Home";
  }

  @FXML
  private void handleMyAccounts() throws IOException {
    content.getChildren().setAll(myAccounts);
    myAccountsController.initData(user, content, account, accountController);
    currentContent = "MyAccounts";
  }

  @FXML
  private void handleTransaction() throws IOException {
    content.getChildren().setAll(transaction);
    transactionController.reset();
    transactionController.initData(user);
    currentContent = "Transaction";
  }

  @FXML
  private void handleMyTransactions() throws IOException {
    content.getChildren().setAll(myTransactions);
    myTransactionsController.initData(user);
    currentContent = "MyTransactions";
  }

  @FXML
  private void handleSavingsCalc() throws IOException {
    content.getChildren().setAll(savingsCalc);
    savingsCalcController.reset();
    currentContent = "SavingsCalc";
  }

  @FXML
  private void handleMyProfile() throws IOException {
    content.getChildren().setAll(profile);
    profileController.initData(user);
    currentContent = "MyProfile";
  }
}
