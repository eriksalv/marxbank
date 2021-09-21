package it1901;

import it1901.util.TextFieldFormatter;
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
    private DataManager dm;

    @FXML private MenuButton myAccountsList;
    @FXML private TextField recieverText;
    @FXML private TextField dateText;
    @FXML private TextField amountText;
    @FXML private Label transactionCompleteMsg;

    private EventHandler<ActionEvent> accountsMenuEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e)
        {
            myAccountsList.setText(((MenuItem)e.getSource()).getText());
        }
    };

    @FXML 
    private void initialize() {
        transactionCompleteMsg.setVisible(false);
        setNumericOnlyTextFields();
    }

    public void initData(User user, DataManager dm) {
        this.user = user;
        this.dm = dm;
        createMyAccountsListItems();
    }

    private void createMyAccountsListItems() {
        user.getAccounts().forEach(acc -> {
            MenuItem item = new MenuItem(String.valueOf(acc.getAccountNumber()));
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
            Transaction t = new Transaction("id", from, reciever, amount, dm);
            transactionCompleteMsg.setVisible(true);
            try {
                dm.save();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            System.err.println("Input is not a number " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        } 
        
    }
    
}
