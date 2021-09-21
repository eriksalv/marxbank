package it1901.components;

import it1901.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TransactionComponent extends Pane {
    
    @FXML private Label date;
    @FXML private Label fromAccountNumber;
    @FXML private Label toAccountNumber;
    @FXML private Label amount;

    public TransactionComponent(Transaction t) {
        date.setText(t.getDateString());
        fromAccountNumber.setText(String.format("%o",t.getFrom().getAccountNumber()));
        toAccountNumber.setText(String.format("%o", t.getReciever().getAccountNumber()));
        amount.setText(String.format("%f", t.getAmount()));
    }

    // add onclick to go to that transaction

}