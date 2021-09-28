package it1901;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeControllerTest extends ApplicationTest{
    
    /**
     * Creating a user and a belonging account to test with.
     */
    private HomeController controller;
    DataManager dm = new DataManager("");
    User user = new User("56789", "annaost", "anna.ostmo@gmail.com", "passord", dm);
    Account account1 = new SavingsAccount("56789", user, 1.5, dm);
    Account account2 = new SavingsAccount("12345", user, dm);
    Transaction transaction = new Transaction("4040", account1, account2, 20.0, dm, true);
    
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Testing if the controller exists and works.
     */
    @Test
    public void testController_main() {
        assertNotNull(controller);
    }

   /**
     * Setting up some necessary values for the account and user to be tested.
     */
    @BeforeAll
    public void setup(){
        account1.setBalance(100.0);
        account1.setName("Annas sparekonto");
        user.addAccount(account1);
        account1.addTransaction(transaction);
        account2.addTransaction(transaction);
        controller.initData(user, dm);
    }

    /**
     * Testing whether the account name label matches the actual name of the account.
     */
    @Test
    public void testAccountName(){
        Label label = lookup("#AccountLabel").query();
        assertTrue(label.getText().equals("Annas brukskonto"));
    }

    /**
     * Testing whether the balance label matches the actual balance of the account.
     */
    @Test
    public void testBalance(){
        Label label = lookup("#AmountLabel").query();
        assertTrue(label.getText().equals(Double.toString(100.0)));
    }

    /**
     * Testing whether the account number label matches the actual account number of the account.
     */
    @Test
    public void testAccountNumber(){
        Label label = lookup("#AccountNumberLabel").query();
        assertTrue(label.getText().equals(Integer.toString(account1.getAccountNumber())));
    }






}
