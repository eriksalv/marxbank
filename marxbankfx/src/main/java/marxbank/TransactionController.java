package marxbank;

import marxbank.util.TextFieldFormatter;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;

public class TransactionController {

    private User user;
    private Account from;
    private Account reciever;
    private double amount;
    private Transaction t;

    @FXML private MenuButton myAccountsList;
    @FXML private TextField recieverText;
    @FXML private TextField dateText;
    @FXML private TextField amountText;
    @FXML private Label transactionCompleteMsg;
    @FXML private Label transactionFailedMsg;

    private final EventHandler<ActionEvent> accountsMenuEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e)
        {
            myAccountsList.setText(((MenuItem)e.getSource()).getText());
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
            from = Bank.getInstanceBank().getAccount(Integer.parseInt(myAccountsList.getText()));
            reciever = Bank.getInstanceBank().getAccount(Integer.parseInt(recieverText.getText()));
            amount = Integer.parseInt(amountText.getText());
            
            t = DataManager.manager().createTransaction(from, reciever, amount);
            transactionFailedMsg.setVisible(false);
            transactionCompleteMsg.setVisible(true);
            try {
                DataManager.manager().save();
            } catch (Exception e) {
                // TODO Auto-generated catch block
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
            //TODO: endre til from.getAvailableBalance() når forfallsdato er ferdig implementert
            transactionFailedMsg.setText("Ikke nok disponibelt beløp på konto: "
             + from.getName() + ". Tilgjengelig beløp: " + from.getBalance());
        }         
    }
    
}
