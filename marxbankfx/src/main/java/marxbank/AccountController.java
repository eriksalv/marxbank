package marxbank;

import java.util.ArrayList;
import java.util.List;

import marxbank.components.TransactionComponent;
import marxbank.model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class AccountController {

    @FXML private Label accountType;
    @FXML private Label balance;
    @FXML private Label accountName;
    @FXML private Label accountNumber;
    @FXML private ListView<TransactionComponent> transactionContainer;

    private Account account;

    public void initData(Account a) {
        account = a;
        accountName.setText(a.getName());
        accountType.setText(a.getAccountType());
        balance.setText("Disponibelt beløp: " + account.getBalance());
        accountNumber.setText("Kontonummer: " + account.getAccountNumber());
        List<TransactionComponent> l = new ArrayList<TransactionComponent>();
        this.account.getTransactions().stream().forEach(t -> l.add(new TransactionComponent(t)));
        ObservableList<TransactionComponent> ol = FXCollections.observableList(l);
        transactionContainer.setItems(ol);
    }
    
}
