package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import marxbank.model.Account;
import marxbank.model.SavingsAccount;
import marxbank.model.Transaction;
import marxbank.model.User;
import marxbank.util.Loader;

public class MyAccountsControllerTest extends ApplicationTest {

  private MyAccountsController controller;
  private User user;
  private Pane content = new Pane();
  private AnchorPane accountPane;
  private AccountController accountController;
  private Account account1;
  private Account account2;

  @TempDir
  static Path tempDir;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("MyAccounts.fxml"));
    Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();

    FXMLLoader accountLoader = Loader.loadFXML(MainController.class, "Account.fxml");
    this.accountPane = accountLoader.load();
    this.accountController = accountLoader.getController();
  }

  /**
   * Sets up tempdir for Datamananger
   */
  @BeforeAll
  static void setup() throws IOException {
    Files.createDirectories(tempDir.resolve("data"));
  }

  @BeforeEach
  public void beforeEachSetup() throws IOException, InterruptedException {
    DataManager.setPath(tempDir.toFile().getCanonicalPath());
    this.user = new User(Long.parseLong("56789"), "annaost", "anna.ostmo@gmail.com", "passord");
    this.account1 = new SavingsAccount(user, "Annas brukskonto");
    this.account1.deposit(500);
    this.account2 = new SavingsAccount(Long.parseLong("12345"), user);
    new Transaction(Long.parseLong("4040"), account1, account2, 20.0, true);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        controller.initData(user, content, accountPane, accountController);
      }
    });
    waitForRunLater();
  }

  @Test
  public void testController() {
    assertNotNull(controller);
  }


  @Test
  @DisplayName("Test handle Select Account")
  public void testHandleSelectAccount() {
    VBox r = lookup("#accountBtns").queryAs(VBox.class);

    clickOn(r.getChildren().get(0));
    assertEquals("accountName", ((Pane) content.getChildren().get(0)).getChildren().get(0).getId());
  }

  @Test
  @DisplayName("Test handle create new account")
  public void testHandleCreateNewAccount() {
    clickOn("#createNewAccountButton");
    assertEquals("createNewAccount", (((Pane) content.getChildren().get(0)).getId()));
  }

  public static void waitForRunLater() throws InterruptedException {
    Semaphore semaphore = new Semaphore(0);
    Platform.runLater(() -> semaphore.release());
    semaphore.acquire();
  }

}
