package marxbank;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import marxbank.model.Account;
import marxbank.model.User;
import marxbank.util.AccountType;
import javafx.scene.control.MenuItem;

public class CreateNewAccountController {
    @FXML private MenuButton selectAccountType;
    @FXML private TextField accountName;
    @FXML private Label creationCompleteMsg;
    @FXML private Label errorMsg;

    private User user;
    private String accName;

    private Account acc;

    public CreateNewAccountController() {
        
    }

    private final EventHandler<ActionEvent> accountsMenuEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e)
        {
            selectAccountType.setText(((MenuItem)e.getSource()).getText());
        }
    };

    public void initData(User user) {
        this.user = user;
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
        System.out.println(accountName.getText());

        errorMsg.setText("");
        if(accName.isBlank()) {
            errorMsg.setText("Konto trenger et navn.");
            return;
        }
        acc = DataManager.manager().createAccount(selectAccountType.getText(), user, accName);
        if (acc==null) {
            errorMsg.setText("Ingen kontotype valgt.");
            return;
        }
        creationCompleteMsg.setText("Ny konto med kontonummer: " + acc.getAccountNumber() + " og navn: " + acc.getName() + " ble opprettet");
        creationCompleteMsg.setVisible(true);
        try {
            DataManager.manager().save();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
