package it1901.components;

import java.io.IOException;

import it1901.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TransactionComponent extends Pane {

    @FXML private Label date;
    @FXML private Label fromAccountNumber;
    @FXML private Label toAccountNumber;
    @FXML private Label amount;

    private Transaction transaction;

    @FXML
    private void initialize() {

        date.setText(transaction.getDateString().split(" ")[0]);
        fromAccountNumber.setText(String.format("%o",transaction.getFrom().getAccountNumber()));
        toAccountNumber.setText(String.format("%o", transaction.getReciever().getAccountNumber()));
        amount.setText(String.format("%.2f", transaction.getAmount()));
        setStyle("-fx-padding: 0px; -fx-margin: 0px;");
    }
    
    public TransactionComponent(Transaction t) {
        this.transaction = t;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TransactionComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add onclick to go to that transaction

}