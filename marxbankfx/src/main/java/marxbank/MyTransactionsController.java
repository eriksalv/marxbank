package marxbank;

import java.util.ArrayList;
import java.util.List;

import marxbank.components.TransactionComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;

public class MyTransactionsController {

    @FXML
    private ListView<TransactionComponent> transactionsContainer;

    public void initData(User user) {

        for (Account a : user.getAccounts()) {
            System.out.println(a.getTransactions());
        }

        if (user.getAccounts().size()==0) {
            return;
        }

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
