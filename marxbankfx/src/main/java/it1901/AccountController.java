package it1901;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccountController {

    @FXML private Label accountType;
    @FXML private Label balance;
    
    private Account account;

    @FXML
    private void initialize() {

    }

    public void initData(Account a) {
        account = a;
        accountType.setText(a.getAccountType());
        balance.setText("Disponibelt beløp: " + account.getBalance());
        /*((SavingsAccount) account).addInterest();*/
    }
}
