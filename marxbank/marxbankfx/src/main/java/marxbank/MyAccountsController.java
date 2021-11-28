package marxbank;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import marxbank.model.Account;
import marxbank.model.User;
import marxbank.util.Loader;

public class MyAccountsController {

  private User user;
  private Account account;

  @FXML
  private VBox myAccounts;
  @FXML
  private VBox accountBtns;

  private Pane content;
  private AnchorPane accountPane;
  private AnchorPane createNewAccount;

  private AccountController accountController;
  private CreateNewAccountController createNewAccountController;

  @FXML
  private void initialize() {
    loadViews();
  }

  private void loadViews() {
    try {
      FXMLLoader loader = Loader.loadFxml(getClass(), "CreateNewAccount.fxml");
      this.createNewAccount = loader.load();
      this.createNewAccountController = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void initData(User user, Pane content, AnchorPane accountPane,
      AccountController accountController) {
    this.content = content;
    this.user = user;
    this.accountPane = accountPane;
    this.accountController = accountController;
    createAccountButtons();
  }

  /**
   * Creates a new button for every account on the users account-list
   */
  private void createAccountButtons() {
    accountBtns.getChildren().clear();
    List<Account> accounts = user.getAccounts();
    for (Account a : accounts) {
      Button accountBtn = new Button();
      accountBtn.getStyleClass().add("accBtn");
      accountBtn.setText("Kontonummer: " + a.getAccountNumber());
      accountBtn.setId(String.format("%d", a.getAccountNumber()));
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
   * 
   * @param e action event
   * @throws IOException if fxml cannot be loaded
   */
  @FXML
  private void handleSelectAccount(ActionEvent e) throws IOException {
    accountController.initData(account);
    content.getChildren().setAll(accountPane);
  }

  @FXML
  private void handleCreateNewAccount(ActionEvent e) throws IOException {
    createNewAccountController.reset();
    createNewAccountController.initData(user);
    content.getChildren().setAll(createNewAccount);
  }

}
