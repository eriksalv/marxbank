package marxbank;

import javafx.event.EventHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import marxbank.model.Account;
import marxbank.model.User;
import marxbank.util.AccountType;
import javafx.scene.control.MenuItem;

public class CreateNewAccountController {
  @FXML
  private MenuButton selectAccountType;
  @FXML
  private TextField accountName;
  @FXML
  private Label creationCompleteMsg;
  @FXML
  private Label errorMsg;
  @FXML
  private Button handleCreateAccountButton;

  private User user;
  private String accName;

  private Account acc;

  private final EventHandler<ActionEvent> accountsMenuEvent = new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent e) {
      selectAccountType.setText(((MenuItem) e.getSource()).getText());
    }
  };

  public void reset() {
    creationCompleteMsg.setVisible(false);
    selectAccountType.setText("Velg kontotype");
    accountName.setText("");
    creationCompleteMsg.setText("");
  }

  public void initData(User user) {
    this.user = user;
  }

  @FXML
  private void initialize() {
    creationCompleteMsg.setVisible(false);
    createSelectAccountTypeItems();
    setBtnDisableProperty();
  }

  private void setBtnDisableProperty() {
    BooleanBinding emptyText = Bindings.isEmpty(selectAccountType.textProperty())
        .or(Bindings.isEmpty(accountName.textProperty()));
    handleCreateAccountButton.disableProperty().bind(emptyText);
  }

  private void createSelectAccountTypeItems() {
    AccountType.stream().forEach(type -> {
      MenuItem item = new MenuItem(type.getTypeString());
      item.setOnAction(accountsMenuEvent);
      selectAccountType.getItems().add(item);
    });
  }

  @FXML
  private void handleCreateAccount() {
    accName = accountName.getText();
    System.out.println(accountName.getText());

    errorMsg.setText("");
    if (accName.isBlank()) {
      errorMsg.setText("Konto trenger et navn.");
      return;
    }
    try {
      acc = DataManager.createAccount(selectAccountType.getText(), user, accName);
    } catch (IllegalArgumentException e) {
      errorMsg.setText(e.getMessage());
      return;
    }
    creationCompleteMsg.setText("Ny konto med kontonummer: " + acc.getAccountNumber() + " og navn: "
        + acc.getName() + " ble opprettet");
    creationCompleteMsg.setVisible(true);
    try {
      DataManager.save();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
