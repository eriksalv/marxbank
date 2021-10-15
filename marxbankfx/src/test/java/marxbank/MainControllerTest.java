package marxbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

public class MainControllerTest extends ApplicationTest {

    private MainController controller;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_test.fxml"));
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
        DataManager.manager().setPath(tempDir.toFile().getCanonicalPath());
        User user = new User("username", "email@email.com", "password");
        controller.initData(user);
    }


    @Test
    public void testController_main() {
        assertNotNull(controller);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForHandleClickBtnsTest")
    public void handleClickBtnsTest(String operation, String expected) {
        clickOn(operation);
        assertEquals(expected, controller.getCurrentContent());
    }

    private static Stream<Arguments> provideStringsForHandleClickBtnsTest() {
        return Stream.of(
            Arguments.of("#menuBtn2", "MyAccounts"),
            Arguments.of("#menuBtn3", "Transaction"),
            Arguments.of("#menuBtn4", "MyTransactions"),
            Arguments.of("#menuBtn5", "MyProfile")
        );
    }

    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }
}
