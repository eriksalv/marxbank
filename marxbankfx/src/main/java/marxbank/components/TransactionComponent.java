package marxbank.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import marxbank.model.Transaction;

public class TransactionComponent extends Pane {

    @FXML private Label date;
    @FXML private Label fromAccountNumber;
    @FXML private Label toAccountNumber;
    @FXML private Label amount;

    private final Transaction transaction;

    @FXML
    private void initialize() {

        date.setText(transaction.getDateString().split(" ")[0]);
        fromAccountNumber.setText(String.format("%d",transaction.getFrom().getAccountNumber()));
        toAccountNumber.setText(String.format("%d", transaction.getReciever().getAccountNumber()));
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