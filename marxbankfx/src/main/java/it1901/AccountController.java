package it1901;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccountController {

    @FXML private Label accountType;
    @FXML private Label balance;
    
    private Account account;

    @FXML
    private void initialize() {
        initTemporaryTestData();
        /*if (account instanceof SavingsAccount) {
            accountType.setText("Sparekonto");
        }*/
        balance.setText("" + account.getBalance());
    }

    private void initTemporaryTestData() {
        account = new SavingsAccount(new User(), 3);
        account.deposit(5000);
        /*((SavingsAccount) account).addInterest();*/
    }
}
