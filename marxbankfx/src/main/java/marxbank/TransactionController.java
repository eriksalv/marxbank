package marxbank;

import marxbank.util.TextFieldFormatter;
import marxbank.model.Account;
import marxbank.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class TransactionController {

  private User user;
  private Account from;
  private Account reciever;
  private double amount;

  @FXML
  private MenuButton myAccountsList;
  @FXML
  private TextField recieverText;
  @FXML
  private TextField dateText;
  @FXML
  private TextField amountText;
  @FXML
  private Label transactionCompleteMsg;
  @FXML
  private Label transactionFailedMsg;

  private final EventHandler<ActionEvent> accountsMenuEvent = new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent e) {
      myAccountsList.setText(((MenuItem) e.getSource()).getText());
    }
  };

  @FXML
  private void initialize() {
    transactionCompleteMsg.setVisible(false);
    transactionFailedMsg.setVisible(false);
    setNumericOnlyTextFields();
  }

  public void initData(User user) {
    this.user = user;
    createMyAccountsListItems();
  }

  private void createMyAccountsListItems() {
    user.getAccounts().forEach(acc -> {
      MenuItem item = new MenuItem(String.valueOf(acc.getAccountNumber()));
      item.setId(String.valueOf(acc.getAccountNumber()));
      item.setOnAction(accountsMenuEvent);
      myAccountsList.getItems().add(item);
    });
  }

  private void setNumericOnlyTextFields() {
    recieverText.setTextFormatter(TextFieldFormatter.getNumberFormatter());
    amountText.setTextFormatter(TextFieldFormatter.getNumberFormatter());
  }

  @FXML
  private void handleCommitTransaction(ActionEvent ev) {
    try {
      from = DataManager.getAccount(Integer.parseInt(myAccountsList.getText()));
      reciever = DataManager.getAccount(Integer.parseInt(recieverText.getText()));
      amount = Integer.parseInt(amountText.getText());

      DataManager.createTransaction(from, reciever, amount);
      transactionFailedMsg.setVisible(false);
      transactionCompleteMsg.setVisible(true);
      try {
        DataManager.save();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      transactionFailedMsg.setVisible(true);
      transactionCompleteMsg.setVisible(false);
      transactionFailedMsg.setText("Noe gikk galt.");
    } catch (IllegalStateException e) {
      System.err.println(e.getMessage());
      transactionFailedMsg.setVisible(true);
      transactionCompleteMsg.setVisible(false);
      transactionFailedMsg.setText("Ikke nok disponibelt beløp på konto: " + from.getName()
          + ". Tilgjengelig beløp: " + from.getBalance());
    }
  }
}
