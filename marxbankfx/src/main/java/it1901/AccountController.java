package it1901;

import java.util.ArrayList;
import java.util.List;

import it1901.components.TransactionComponent;
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

    private DataManager dm;
    private User user;
    private Account account;

    @FXML
    private void initialize() {
        System.out.println("yeet");
        // dm = new DataManager("../data");
        // user = new User("1", "username", "email@email.com", "password", dm);
        // Account a1 = new SavingsAccount("1", user, 5.0, dm);
        // Account a2 = new SavingsAccount("2", user, 2.0, dm);

        // a1.deposit(5000);

        // Transaction t1 = new Transaction("1", a1, a2, 500, dm);
        // Transaction t2 = new Transaction("2", a1, a2, 50, dm);
        // Transaction t3 = new Transaction("3", a1, a2, 50, dm);
        // Transaction t4 = new Transaction("4", a1, a2, 50, dm);
        // Transaction t5 = new Transaction("5", a1, a2, 50, dm);
        // Transaction t6 = new Transaction("6", a1, a2, 50, dm);
        // Transaction t7 = new Transaction("7", a1, a2, 50, dm);
        // Transaction t8 = new Transaction("8", a1, a2, 50, dm);
        // Transaction t9 = new Transaction("9", a1, a2, 50, dm);


        // initData(a1);
    }

    public void initData(Account a, DataManager dm) {
        this.dm = dm;
        account = a;
        accountType.setText(a.getAccountType());
        balance.setText("Disponibelt beløp: " + account.getBalance());
        accountNumber.setText("Kontonummer: " + account.getAccountNumber());
        List<TransactionComponent> l = new ArrayList<TransactionComponent>();
        this.account.getTransactions().stream().forEach(t -> l.add(new TransactionComponent(t)));
        ObservableList<TransactionComponent> ol = FXCollections.observableList(l);
        transactionContainer.setItems(ol);
    }
    
}
