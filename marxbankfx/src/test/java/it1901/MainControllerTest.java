package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainControllerTest extends ApplicationTest {
    
    private MainController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_test.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
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
            Arguments.of("#menuBtn5", "MyProfile"),
            Arguments.of("#menuBtn1", "Home")
        );
    }
}
