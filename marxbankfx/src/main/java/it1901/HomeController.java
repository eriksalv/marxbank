package it1901;

import javafx.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class HomeController {

    private User user;
    private DataManager dm;

    @FXML private Label HomeLabel;
    @FXML private Label AccountLabel;
    @FXML private Label AccountNumberLabel;
    @FXML private Label AmountLabel;
    @FXML private Label LastActivityLabel;
    @FXML private Label DateLabel;
    @FXML private Label otherAccountLabel;
    @FXML private Label LAaccountLabel;
    @FXML private Label LAamountLabel;
    @FXML private Button AccountButton;
    

    @FXML
    private void initialize() {
    }

    public void initData(User user, DataManager dm) {
        this.user = user;
        this.dm = dm;
        createFavorites();
        recentActivity();
    }

    private void createFavorites() {
        if (user.getAccounts().size() > 0) {
            Account a = user.getAccounts().get(0);
            AccountLabel.setText(a.getName());
            AccountNumberLabel.setText(Integer.toString(a.getAccountNumber()));
            AmountLabel.setText("kr " + Double.toString(a.getBalance()));
        }
    }


    private void recentActivity() {
        if (user.getAccounts().size() > 0) {
            int index = user.getAccounts().get(0).getNumberOfTransactions();
            Transaction recentTransaction = user.getAccounts().get(0).getTransactions().get(index-1);

            for (int i = 1; i < user.getAccounts().size(); i++) {
                int lastIndex = user.getAccounts().get(i).getNumberOfTransactions();
                Transaction lastTransactionOnAccount = user.getAccounts().get(i).getTransactions().get(lastIndex - 1);

                if (lastTransactionOnAccount.getTransactionDate().isAfter(recentTransaction.getTransactionDate())) {
                    recentTransaction = lastTransactionOnAccount;
                }
            }

            DateLabel.setText(recentTransaction.getDateString());
            otherAccountLabel.setText(recentTransaction.getReciever().getName());
            LAaccountLabel.setText(recentTransaction.getFrom().getName());
            LAamountLabel.setText(Double.toString(recentTransaction.getAmount()));
        }

    }




    /**
     * Replaces all nodes in myAccounts VBox with the content of Account.fxml, and "transfers" the
     * selected account to AccountController
     * @param e
     * @throws IOException
     */
    @FXML
    private void handleSelectAccount(ActionEvent e) throws IOException {       
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Account.fxml"));
        AnchorPane pane = loader.load();
        AccountController controller = loader.getController();
        controller.initData(user.getAccounts().get(0), dm);

        //myAccounts.getChildren().setAll(pane);
    }

}
