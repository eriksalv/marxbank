package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfileControllerTest extends ApplicationTest{
    private DataManager dm;
    private ProfileController controller;
    private User user;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
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
    private void beforeEach() throws IOException, InterruptedException {
        resetSingleton();
        this.dm = new DataManager(tempDir.toFile().getCanonicalPath());
        user = new User("username", "email@email.com", "password", dm);
        dm.setLoggedInUser(user);
        Platform.runLater(new Runnable(){
            @Override public void run() {
                controller.initData(user, dm);
            }
        });
        waitForRunLater();
    }

    @Test
    public void testController() {
        assertNotNull(controller);
        assertEquals(user.getId(), lookup("#IDLabel").queryAs(Label.class).getText());
        assertEquals(user.getUsername(), lookup("#usernameLabel").queryAs(Label.class).getText());
    }

    @Test
    @DisplayName("Test opening and closing the pane for changing password")
    public void testTogglePasswordPane() {
        clickOn("#changePasswordButton");
        Pane changePasswordPane = lookup("#newPane").queryAs(Pane.class);
        assertEquals("changePasswordPane", ((AnchorPane)changePasswordPane.getChildren().get(0)).getId());
        clickOn("#closeButton");
        assertTrue(changePasswordPane.getChildren().size()==0);
    }

    @Test
    public void testSignOut() {
        assertEquals(user, dm.getLoggedInUser());
        clickOn("#signOutButton");
        AnchorPane logInScreen = lookup("#root").queryAs(AnchorPane.class);
        assertEquals(logInScreen.getId(), "root");
        assertNull(dm.getLoggedInUser());
    }

    @Test
    @DisplayName("Test currentPassword field does not match users password")
    public void testInvalidPassword() {
        clickOn("#changePasswordButton");
        clickOn("#currentPasswordField").write("wrongPassword");
        clickOn("#saveButton");
        assertEquals("Feil passord", lookup("#saveButton").queryAs(Button.class).getText());
    }

    @Test
    public void testPasswordsDontMatch() {
        clickOn("#changePasswordButton");
        clickOn("#currentPasswordField").write(user.getPassword());
        clickOn("#newPasswordField").write("newPassword");
        clickOn("#saveButton");
        assertEquals("Passordene stemmer ikke", lookup("#saveButton").queryAs(Button.class).getText());
    }

    @Test
    public void testChangePassword() {
        clickOn("#changePasswordButton");
        clickOn("#currentPasswordField").write(user.getPassword());
        clickOn("#newPasswordField").write("newPassword");
        clickOn("#confirmNewPasswordField").write("newPassword");
        clickOn("#saveButton");
        assertEquals("Oppdatert", lookup("#saveButton").queryAs(Button.class).getText());
        assertEquals("newPassword", user.getPassword());
        assertEquals("newPassword", lookup("#passwordLabel").queryAs(Label.class).getText());
    }

    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }

    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> semaphore.release());
        semaphore.acquire();    
    }
}
