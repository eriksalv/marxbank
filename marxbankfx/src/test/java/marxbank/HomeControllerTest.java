package marxbank;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import marxbank.model.Account;
import marxbank.model.SavingsAccount;
import marxbank.model.Transaction;
import marxbank.model.User;

public class HomeControllerTest extends ApplicationTest{
    
    /**
     * Creating a user and a belonging account to test with.
     */
    private HomeController controller;
    private User user;
    private Account account1;
    private Account account2;
    private Transaction transaction;
    private Parent root;
    
    @TempDir
    static Path tempDir;
    
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        this.root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * sets up tempDir for DataMananger
     * @throws IOException
     */
    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectories(tempDir.resolve("data"));
    }

    /**
     * Sets up all the data that is getting used in the test
     * @throws IOException
     */
    @BeforeEach
    void beforeEachSetup() throws IOException {
        DataManager.manager().setPath(tempDir.toFile().getCanonicalPath());
        this.user = new User("56789", "annaost", "anna.ostmo@gmail.com", "passord");
        this.account1 = new SavingsAccount(user, "Annas brukskonto");
        this.account1.deposit(500);
        this.account2 = new SavingsAccount("12345", user);
        this.transaction = new Transaction("4040", account1, account2, 20.0, true);
    }

    /**
     * Setting up some necessary values for the account and user to be tested.
     */
    // @BeforeAll
    // public void setup(){
    //     account1.setBalance(100.0);
    //     account1.setName("Annas sparekonto");
    //     user.addAccount(account1);
    //     account1.addTransaction(transaction);
    //     account2.addTransaction(transaction);
    //     controller.initData(user, dm);
    // }

    /**
     * Testing if the controller exists and works.
     */
    @Test
    public void testController_main() {
        assertNotNull(controller);
    }

    /**
     * Testing whether the account name label matches the actual name of the account.
     */
    @Test
    public void testAccountName(){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                controller.initData(user);
                Label label = lookup("#AccountLabel").query();
                assertNotNull(label);
                assertTrue(label.getText().equals("Annas brukskonto"));
            }
        });

    }

    /**
     * Testing whether the balance label matches the actual balance of the account.
     */
    @Test
    public void testBalance(){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                controller.initData(user);
                Label label = lookup("#AmountLabel").query();
                assertTrue(label.getText().equals("kr " + account1.getBalance()));
            }
        });

    }

    /**
     * Testing whether the account number label matches the actual account number of the account.
     */
    @Test
    public void testAccountNumber(){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                controller.initData(user);
                Label label = lookup("#AccountNumberLabel").query();
                assertTrue(label.getText().equals(Integer.toString(account1.getAccountNumber())));
            }
        });

    }

   /**
     * Testing whether the date for the transaction is represented with the correct label.
     */
    @Test
    public void testDate(){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                controller.initData(user);
                Label label = lookup("#DateLabel").query();
                assertTrue(label.getText().equals(transaction.getDateString()));
            }
        });

    }

    /**
     * Testing whether the label for the account that has recieved or been deducted money from matches the correct account.
     */
    @Test
    public void testRelevantAccount(){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                controller.initData(user);
                Label label = lookup("#LAaccountLabel").query();
                assertTrue(label.getText().equals("Fra: " + transaction.getFrom().getName()));
            }
        });

    }

    /**
     * Testing whether the label for the account that money was sent from/to matches the actual account.
     */
    @Test
    public void testOtherAccount(){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                controller.initData(user);
                Label label = lookup("#OtherAccountLabel").query();
                assertTrue(label.getText().equals("Til: " + transaction.getReciever().getName()));
            }
        });

    }

    /**
     * Testing whether the label for the account that money was sent from/to matches the actual account.
     */
    @Test
    public void testAmount(){

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                controller.initData(user);
                Label label = lookup("#LAamountLabel").query();
                assertTrue(label.getText().equals("kr " + Double.toString(transaction.getAmount())));
            }
        });

    }

}
