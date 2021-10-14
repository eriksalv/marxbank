package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import it1901.model.Account;
import it1901.model.SavingsAccount;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MyAccountsControllerTest extends ApplicationTest {
    
    private MyAccountsController controller;
    private DataManager dm;
    private User user;
    private Account account1;
    private Account account2;
    private Transaction transaction;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("MyAccounts.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Sets up tempdir for Datamananger
     */
    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectories(tempDir.resolve("data"));
    }

    @BeforeEach
    public void beforeEachSetup() throws IOException {
        resetSingleton();
        this.dm = new DataManager(tempDir.toFile().getCanonicalPath());
        this.user = new User("56789", "annaost", "anna.ostmo@gmail.com", "passord", dm);
        this.account1 = new SavingsAccount(user, dm, "Annas brukskonto");
        this.account1.deposit(500);
        this.account2 = new SavingsAccount("12345", user, dm);
        this.transaction = new Transaction("4040", account1, account2, 20.0, dm, true);
        Platform.runLater(new Runnable(){
            @Override public void run() {
                controller.initData(user, dm);
            }
        });
    }

    @Test
    public void testController() {
        assertNotNull(controller);
    }


    //@Test
    @DisplayName("Test handle Select Account")
    public void testHandleSelectAccount() {
        //clickOn("#menuBtn2");
        VBox content = lookup("#myAccounts").queryAs(VBox.class);
        VBox r = lookup("#accountBtns").queryAs(VBox.class);

        clickOn(r.getChildren().get(0));
        assertEquals("accountName", ((AnchorPane)content.getChildren().get(0)).getChildren().get(0).getId());
    }

    //@Test
    @DisplayName("Test handle create new account")
    public void testHandleCreateNewAccount() {
        //clickOn("#menuBtn2");
        VBox content = lookup("#myAccounts").queryAs(VBox.class);
        clickOn("#createNewAccountButton");
        assertEquals("createNewAccount", ((AnchorPane)content.getChildren().get(0)).getChildren().get(0).getId());
    }

    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }

}
