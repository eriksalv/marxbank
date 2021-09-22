package it1901;

import java.util.UUID;

import it1901.util.AccountType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;

public class createNewAccountController {
    
    @FXML private MenuButton selectAccountType;
    @FXML private TextField accountName;
    @FXML private Label creationCompleteMsg;

    private User user;
    private DataManager dm;
    private String accName;

    private Account acc;

    private EventHandler<ActionEvent> accountsMenuEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e)
        {
            selectAccountType.setText(((MenuItem)e.getSource()).getText());
        }
    };

    public createNewAccountController(User user, DataManager dm) {
        this.user = user;
        this.dm = dm;
    }

    @FXML
    private void initialize() {
        creationCompleteMsg.setVisible(false);
        createSelectAccountTypeItems();
    }

    private void createSelectAccountTypeItems() {
        AccountType.stream().forEach(type -> {
            MenuItem item = new MenuItem(type.getTypeString());
            item.setOnAction(accountsMenuEvent);
            selectAccountType.getItems().add(item);
        });
    }

    @FXML
    private void handleCreateAccount() {
        accName = accountName.getText();
        switch (selectAccountType.getText()) { //er det en bedre måte å gjøre dette på???
            case "Sparekonto":
                acc = new SavingsAccount(UUID.randomUUID().toString(), user, dm, accName); //trenger ikke håndtere exceptions her pga implementasjonen til Account
        }
        if (acc==null) {
            System.err.println("No account type selected");
            return;
        }
        creationCompleteMsg.setText("Ny konto med kontonummer: " + acc.getAccountNumber() + " og navn: " + acc.getName() + " ble opprettet");
        creationCompleteMsg.setVisible(true);
        try {
            dm.save();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
