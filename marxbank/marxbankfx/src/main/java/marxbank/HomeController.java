package marxbank;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;


public class HomeController {

  private User user;

  @FXML
  private Pane content;
  @FXML
  private Label homeLabel;
  @FXML
  private Label accountLabel;
  @FXML
  private Label accountNumberLabel;
  @FXML
  private Label amountLabel;
  @FXML
  private Label lastActivityLabel;
  @FXML
  private Label dateLabel;
  @FXML
  private Label otherAccountLabel;
  @FXML
  private Label secondAccountLabel;
  @FXML
  private Label secondAmountLabel;
  @FXML
  private Button accountButton;

  private AnchorPane account;
  private AccountController accountController;

  @FXML
  private void initialize() {
    if (user != null) {
      createFavorites();
    }
  }


  public void initData(User user, Pane content, AnchorPane account,
      AccountController accountController) {
    this.content = content;
    this.user = user;
    this.account = account;
    this.accountController = accountController;
    createFavorites();
    recentActivity();
  }

  /**
   * Adding the first account of the user's accounts into the favorite label at the home side.
   */
  private void createFavorites() {
    if (user.getAccounts().size() == 0) {
      return;
    }
    Account a = user.getAccounts().get(0);
    accountLabel.setText(a.getName());
    accountNumberLabel.setText(Integer.toString(a.getAccountNumber()));
    amountLabel.setText("kr " + Double.toString(a.getBalance()));
  }


  /**
   * Finding the recent transaction that the user is involved in.
   */
  private void recentActivity() {
    if (user != null && user.getAccounts().size() > 0) {
      List<Transaction> transactions = new ArrayList<>();

      for (Account acc : user.getAccounts()) {
        if (acc.getNumberOfTransactions() != 0) {
          int index = acc.getNumberOfTransactions() - 1;
          transactions.add(acc.getTransactions().get(index));
        }
      }

      if (transactions.size() > 0) {
        Transaction recentTransaction = transactions.get(0);

        for (int i = 1; i < transactions.size(); i++) {
          if (transactions.get(i).getTransactionDate()
              .isAfter(recentTransaction.getTransactionDate())) {
            recentTransaction = transactions.get(i);
          }
        }
        dateLabel.setText(recentTransaction.getDateString());
        otherAccountLabel.setText("Til: " + recentTransaction.getReciever().getName());
        secondAccountLabel.setText("Fra: " + recentTransaction.getFrom().getName());
        secondAmountLabel.setText("kr " + Double.toString(recentTransaction.getAmount()));
      } else {
        dateLabel.setText("Ingen nylig aktivitet");
        otherAccountLabel.setText("");
        secondAccountLabel.setText("");
        secondAmountLabel.setText("");
      }
    }
  }



  /**
   * Replaces all nodes in myAccounts VBox with the content of Account.fxml, and "transfers" the
   * selected account to AccountController
   * 
   * @param e action event
   * @throws IOException if it cannot load fxml
   */
  @FXML
  private void handleSelectAccount(ActionEvent e) throws IOException {
    accountController
        .initData(DataManager.getAccount(Integer.parseInt(accountNumberLabel.getText())));
    content.getChildren().setAll(account);
  }

}
