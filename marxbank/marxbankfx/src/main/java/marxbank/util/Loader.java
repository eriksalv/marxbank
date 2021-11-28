package marxbank.util;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Loader {

  /**
   * Helper-method for loading fxml
   * 
   * @param <T> - type of the controller class
   * @param cl - class of the controller
   * @param fileName - filename of fxml file
   * @return FXMLLoader with location set
   */
  public static <T> FXMLLoader loadFXML(Class<T> cl, String fileName) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(cl.getResource(fileName));
    return loader;
  }

  /**
   * Loads fxml and changes scene
   * 
   * @param <T> type of the controller class
   * @param cl - class of the controller
   * @param fileName - filename of fxml file
   * @param e - event that triggered scene change
   * @throws IOException
   */
  public static <T> void changeScene(Class<T> cl, String fileName, Event e) throws IOException {
    FXMLLoader loader = loadFXML(cl, fileName);
    Parent tableViewParent = loader.load();

    Scene tableViewScene = new Scene(tableViewParent);

    Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

    window.setScene(tableViewScene);
    window.show();
  }
}
