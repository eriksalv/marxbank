package it1901;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogInControllerTest extends ApplicationTest {

    private static LogInController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeAll
    static void setup() throws Exception {
        User user = new User("tu", "testUser", "test@email.com", "password", controller.getDM());
        controller.getDM().save();
    }

    @Test
    @DisplayName("test logIn")
    public void testLogIn() {

    }

}
