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
    
    private HomeController controller;
    DataManager dm = new DataManager("");
    User user = new User("56789", "annaost", "anna.ostmo@gmail.com", "passord", dm);
    Account account1 = new SavingsAccount("56789", user, 1.5, dm);
    
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @Test
    public void testController_main() {
        assertNotNull(controller);
    }

    @BeforeAll
    public void setup(){
        account1.setBalance(100.0);
        account1.setName("Annas sparekonto");
        user.addAccount(account1);
        controller.initData(user, dm);

        
    }

    @Test
    public void testAccountName(){
        Label label = lookup("#AccountLabel").query();
        assertTrue(label.getText().equals("Annas brukskonto"));
    }

    @Test
    public void testBalance(){
        Label label = lookup("#AmountLabel").query();
        assertTrue(label.getText().equals(Double.toString(100.0)));
    }

    @Test
    public void testAccountNumber(){
        Label label = lookup("#AccountNumberLabel").query();
        assertTrue(label.getText().equals(Integer.toString(account1.getAccountNumber())));
    }




}
