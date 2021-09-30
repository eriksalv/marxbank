package it1901;

import java.util.ArrayList;
import java.util.List;

import it1901.components.TransactionComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MyTransactionsController {

    @FXML
    private ListView<TransactionComponent> transactionsContainer;

    public void initData(User user) {

        System.out.println(user.getAccounts().get(0).getTransactions());

        List<Transaction> ml = new ArrayList<Transaction>();
        List<TransactionComponent> l = new ArrayList<TransactionComponent>();
        user.getAccounts().stream().forEach(e -> {
            e.getTransactions().stream().forEach(t -> {
                if(!ml.contains(t)) ml.add(t);
            });
        });

        ml.stream().forEach(t -> l.add(new TransactionComponent(t)));

        ObservableList<TransactionComponent> ol = FXCollections.observableList(l);
        transactionsContainer.setItems(ol);
    }

}
