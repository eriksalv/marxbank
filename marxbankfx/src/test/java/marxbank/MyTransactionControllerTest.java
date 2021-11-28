package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marxbank.model.Account;
import marxbank.model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MyTransactionControllerTest extends ApplicationTest {

  private TransactionController controller;
  private Account a;
  private Account b;
  private User user;

  @TempDir
  static Path tempDir;

  @Override
  public void start(final Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Transaction.fxml"));
    final Parent root = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * sets up tempDir for DataMananger
   * 
   * @throws IOException
   */
  @BeforeAll
  static void setup() throws IOException {
    Files.createDirectories(tempDir.resolve("data"));
  }

  @BeforeEach
  private void beforeEach() throws IOException {
    DataManager.resetData();
    DataManager.setPath(tempDir.toFile().getCanonicalPath());
    user = new User("username", "email@email.com", "password");
    a = DataManager.createAccount("Sparekonto", user, "test1");
    b = DataManager.createAccount("Sparekonto", user, "test2");
    controller.initData(user);
  }

  @Test
  public void testController() {
    assertNotNull(controller);
  }

  @Test
  public void testValidTransaction() {
    a.deposit(100);
    assertEquals(100, a.getBalance());
    clickOn("#myAccountsList").clickOn("#" + a.getAccountNumber());
    clickOn("#recieverText").write("#" + b.getAccountNumber());
    clickOn("#amountText").write("100");
    clickOn("#transactionBtn");
    assertEquals(0, a.getBalance());
    assertEquals(100, b.getBalance());
    assertEquals("Overføringen var vellykket",
        ((Label) lookup("#transactionCompleteMsg").query()).getText());
  }

  @Test
  public void testEmptyAccountNum() {
    a.deposit(100);
    clickOn("#amountText").write("100");
    Button btn = (Button) lookup("#transactionBtn").queryAs(Button.class);
    assertTrue(btn.isDisabled());
  }

  @Test
  public void testInvalidAccount() {
    a.deposit(100);
    clickOn("#recieverText").write("#" + b.getAccountNumber());
    clickOn("#amountText").write("100");
    clickOn("#transactionBtn");
    assertEquals(100, a.getBalance());
    assertEquals(0, b.getBalance());
    assertEquals("Noe gikk galt.", ((Label) lookup("#transactionFailedMsg").query()).getText());
  }

  @Test
  public void testInvalidAmount() {
    a.deposit(100);
    a.setName("name");
    clickOn("#myAccountsList").clickOn("#" + a.getAccountNumber());
    clickOn("#recieverText").write("#" + b.getAccountNumber());
    clickOn("#amountText").write("101");
    clickOn("#transactionBtn");
    assertEquals(100, a.getBalance());
    assertEquals(0, b.getBalance());
    assertEquals("Not enough balance on account",

        ((Label) lookup("#transactionFailedMsg").query()).getText());
  }
}
