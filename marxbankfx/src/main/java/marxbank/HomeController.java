package marxbank;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;


public class HomeController {

    private User user;
    
    @FXML private AnchorPane home;
    @FXML private Label HomeLabel;
    @FXML private Label AccountLabel;
    @FXML private Label AccountNumberLabel;
    @FXML private Label AmountLabel;
    @FXML private Label LastActivityLabel;
    @FXML private Label DateLabel;
    @FXML private Label OtherAccountLabel;
    @FXML private Label LAaccountLabel;
    @FXML private Label LAamountLabel;
    @FXML private Button AccountButton;
    

    @FXML
    private void initialize() {
        if(user != null) {
            createFavorites();
        }
    }


    public void initData(User user) {
        this.user = user;
        createFavorites();
        recentActivity();
    }

    /**
     * Adding the first account of the user's accounts into the favorite label at the home side. 
     */
    private void createFavorites() {   
        Account a = user.getAccounts().get(0);
        AccountLabel.setText(a.getName());
        AccountNumberLabel.setText(Integer.toString(a.getAccountNumber()));
        AmountLabel.setText("kr " + Double.toString(a.getBalance()));
    }


    /**
     * Finding the recent transaction that the user is involved in. 
     */
    private void recentActivity() {   
        if (user != null && user.getAccounts().size() > 0) {
            List<Transaction> transactions = new ArrayList<>();
            
            for (Account acc : user.getAccounts()) {
                if (acc.getNumberOfTransactions() != 0) {
                    int index = acc.getNumberOfTransactions() - 1;
                    transactions.add(acc.getTransactions().get(index));
                }
            }

            if (transactions.size() > 0) {
                Transaction recentTransaction = transactions.get(0);

                for (int i = 1; i < transactions.size(); i++) {
                    if (transactions.get(i).getTransactionDate().isAfter(recentTransaction.getTransactionDate())) {
                        recentTransaction = transactions.get(i);
                    }
                } 
                DateLabel.setText(recentTransaction.getDateString());
                OtherAccountLabel.setText("Til: " + recentTransaction.getReciever().getName());
                LAaccountLabel.setText("Fra: " + recentTransaction.getFrom().getName());
                LAamountLabel.setText("kr " + Double.toString(recentTransaction.getAmount()));
            }

            else {
                DateLabel.setText("Ingen nylig aktivitet");
                OtherAccountLabel.setText("");
                LAaccountLabel.setText("");
                LAamountLabel.setText("");
            }
            
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
        controller.initData(Bank.getInstanceBank().getAccount(Integer.parseInt(AccountNumberLabel.getText())));

        home.getChildren().setAll(pane);
    }

}
