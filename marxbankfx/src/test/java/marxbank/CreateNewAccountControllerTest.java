package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import javafx.stage.Stage;
import marxbank.Bank;
import marxbank.model.Account;
import marxbank.model.SavingsAccount;
import marxbank.model.Transaction;
import marxbank.model.User;

public class CreateNewAccountControllerTest extends ApplicationTest{
    private CreateNewAccountController controller;
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
        DataManager.manager().setPath(tempDir.toFile().getCanonicalPath());
        this.user = new User("56789", "annaost", "anna.ostmo@gmail.com", "passord");
        this.account1 = new SavingsAccount(user, "Annas brukskonto");
        this.account1.deposit(500);
        this.account2 = new SavingsAccount("12345", user);
        this.transaction = new Transaction("4040", account1, account2, 20.0, true);
        this.controller.initData(user);
    }

    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }

    @Test
    @DisplayName("test create new account no name")
    public void testCreateNewAccountNoName() {
        clickOn("#handleCreateAccountButton");
        assertEquals("Konto trenger et navn.", lookup("#errorMsg").queryAs(Label.class).getText());
    }

    @Test
    @DisplayName("test create new account no account type")
    public void testCreateNewAccountNoType() {
        clickOn("#accountName").write("hello");
        clickOn("#handleCreateAccountButton");
        assertEquals("Ingen kontotype valgt.", lookup("#errorMsg").queryAs(Label.class).getText());
    }

    @Test
    @DisplayName("test create new account succesfull")
    public void testCreateNewAccount() {
        Label completeLabel = (Label) lookup("#creationCompleteMsg").queryLabeled();
        clickOn("#accountName").write("hello");
        MenuButton b = lookup("#selectAccountType").queryAs(MenuButton.class);
        clickOn(b).moveBy(0, 25).clickOn(MouseButton.PRIMARY);

        clickOn("#handleCreateAccountButton");
        assertTrue(completeLabel.isVisible());
    }
}
