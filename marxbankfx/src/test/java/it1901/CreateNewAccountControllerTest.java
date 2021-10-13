package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateNewAccountControllerTest extends ApplicationTest{
    private CreateNewAccountController controller;
    private DataManager dm;
    private User user;
    private Account account1;
    private Account account2;
    private Transaction transaction;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewAccount.fxml"));
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
        this.controller.initData(user, dm);
    }

    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }

    @Test
    @DisplayName("test create new account no name")
    public void testCreateNewAccountNoName() {
        //clickOn("#menuBtn2");
        //clickOn("#createNewAccountButton");
        clickOn("#handleCreateAccountButton");
        assertEquals("Account needs a name.", lookup("#errorMsg").queryAs(Label.class).getText());
    }

    @Test
    @DisplayName("test create new account no account type")
    public void testCreateNewAccountNoType() {
        //clickOn("#menuBtn2");
        //clickOn("#createNewAccountButton");
        clickOn("#accountName").write("hello");
        clickOn("#handleCreateAccountButton");
        assertEquals("No account type selected.", lookup("#errorMsg").queryAs(Label.class).getText());
    }

    @Test
    @DisplayName("test create new account succesfull")
    public void testCreateNewAccount() {
        //clickOn("#menuBtn2");
        //clickOn("#createNewAccountButton");
        Label completeLabel = (Label) lookup("#creationCompleteMsg").queryLabeled();
        clickOn("#accountName").write("hello");
        MenuButton b = lookup("#selectAccountType").queryAs(MenuButton.class);
        clickOn(b).moveBy(0, 25).clickOn(MouseButton.PRIMARY);
        //clickOn(b).clickOn(b.getChildrenUnmodifiable().get(0));
        //System.out.println(((StackPane)b.getChildrenUnmodifiable().get(1)));

        clickOn("#handleCreateAccountButton");
        assertTrue(completeLabel.isVisible());
    }
}
