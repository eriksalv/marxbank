package marxbank;

import marxbank.util.TextFieldFormatter;
import marxbank.model.Account;
import marxbank.model.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
  private TextField amountText;
  @FXML
  private Label transactionCompleteMsg;
  @FXML
  private Button transactionBtn;
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
    setBtnDisableProperty();
  }

  private void setBtnDisableProperty() {
    BooleanBinding emptyText = Bindings.isEmpty(myAccountsList.textProperty())
        .or(Bindings.isEmpty(recieverText.textProperty()))
        .or(Bindings.isEmpty(amountText.textProperty()));
    transactionBtn.disableProperty().bind(emptyText);
  }

  public void initData(User user) {
    this.user = user;
    createMyAccountsListItems();
  }

  private void createMyAccountsListItems() {
    myAccountsList.getItems().clear();
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

  public void reset() {
    transactionCompleteMsg.setVisible(false);
    transactionFailedMsg.setVisible(false);
    myAccountsList.setText("Velg konto");
    recieverText.setText("");
    amountText.setText("");
    transactionFailedMsg.setText("");
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
      transactionFailedMsg.setText(e.getMessage());
    }
  }
}
