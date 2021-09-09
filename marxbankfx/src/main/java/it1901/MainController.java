package it1901;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML private TabPane tabs;

    @FXML
    private void initialize() {
        int i = 0;
        for (Tab t : tabs.getTabs()) {
            if (i==0) {
                t.setStyle("-fx-border-width:0 0 1 0; -fx-border-style:solid; -fx-border-color:black;");
            } else {
                t.setStyle("-fx-border-width:0 1px 1px 0; -fx-border-style:solid; -fx-border-color:black;");
            }
            i++;
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
