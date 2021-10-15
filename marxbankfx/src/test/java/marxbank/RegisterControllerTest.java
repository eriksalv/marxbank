package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marxbank.Bank;
import marxbank.model.User;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class RegisterControllerTest extends ApplicationTest {

    private RegisterController controller;
    private User existingUser;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        final Parent root = loader.load();
        controller = loader.getController();
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

    @BeforeEach
    private void beforeEach() throws IOException {
        resetSingleton();
        DataManager.manager().resetData();
        DataManager.manager().setPath(tempDir.toFile().getCanonicalPath());
        existingUser = DataManager.manager().createUser("username", "email@email.com", "password");
    }

    @Test
    public void testController() {
        assertNotNull(controller);
    }

    @Test
    public void testValidNewUser() {
        clickOn("#usernameText").write("🎃🎃🎃🎃🎃");
        clickOn("#emailText").write("email@email.com");
        clickOn("#password1Text").write("password");
        clickOn("#password2Text").write("password");
        clickOn("#registerBtn");
        assertTrue(DataManager.manager().getIndexOfUser(existingUser) != -1 && DataManager.manager().checkIfUsernameIsTaken("🎃🎃🎃🎃🎃"));
    }

    @Test
    public void testUsernameAlreadyTaken() {
        clickOn("#usernameText").write("username");
        clickOn("#emailText").write("email@email.com");
        clickOn("#password1Text").write("pass");
        clickOn("#password2Text").write("pass");
        clickOn("#registerBtn");
        assertTrue(DataManager.manager().getIndexOfUser(existingUser) != -1 && DataManager.manager().getUsers().size()==1);
    }

    @Test
    public void testInvalidUsername() {
        clickOn("#usernameText").write("thisUsernameIsTooLong💀💀💀💀💀💀💀💀💀💀");
        clickOn("#emailText").write("email@email.com");
        clickOn("#password1Text").write("pass");
        clickOn("#password2Text").write("pass");
        clickOn("#registerBtn");
        assertTrue(DataManager.manager().getIndexOfUser(existingUser) != -1 && DataManager.manager().getUsers().size()==1);
        assertEquals("username is too long, must be 30 characters maximum.", ((Label) lookup("#registerFailedMsg").query()).getText());
    }

    @Test
    public void testPasswordsDontMatch() {
        clickOn("#usernameText").write("user");
        clickOn("#emailText").write("email@email.com");
        clickOn("#password1Text").write("rightPassword");
        clickOn("#password2Text").write("wrongPassword");
        clickOn("#registerBtn");
        assertTrue(DataManager.manager().getIndexOfUser(existingUser) != -1 && DataManager.manager().getUsers().size()==1);
        assertEquals("Passwords dont match", ((Label) lookup("#registerFailedMsg").query()).getText());
    }


    @Test
    public void testCancel() {
        clickOn("#cancelBtn");
        AnchorPane logInScreen = lookup("#root").queryAs(AnchorPane.class);
        assertEquals(logInScreen.getId(), "root");
        assertNull(DataManager.manager().getLoggedInUser());
    }


    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }
}
