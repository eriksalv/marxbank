package it1901;

import it1901.components.TransactionComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AccountController {

    @FXML private Label accountType;
    @FXML private Label balance;
    @FXML private Label accountName;
    @FXML private Pane transactionContainer;

    private Account account;

    @FXML
    private void initialize() {

    }

    public void initData(Account a) {
        account = a;
        accountType.setText(a.getAccountType());
        balance.setText("Disponibelt beløp: " + account.getBalance());
        this.account.getTransactions().stream().forEach(t -> createTransactions(t));
    }

    private void createTransactions(Transaction t) {
        TransactionComponent tc = new TransactionComponent(t);
        transactionContainer.getChildren().add(tc);
    }
}
