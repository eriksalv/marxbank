package marxbank;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SavingsCalcControllerTest extends ApplicationTest {

    private SavingsCalcController controller;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SavingsCalc.fxml"));
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

    @Test
    public void testController() {
        assertNotNull(controller);
    }

    /**
     * Testing wether the text field "totalAmountText" is correct based on the valid parameters.
     */
    @Test
    @DisplayName("test a calculation with valid parameters")
    public void testValidCalculation() {
        int sum = 1331;
        clickOn("#monthlyAmount").write("0");
        clickOn("#lumpAmount").write("1000");
        clickOn("#period").write("3");
        clickOn("#interestRate").write("10");
        clickOn("#findTotalAmount");

        Label label = lookup("#totalAmountText").query();

        System.out.println(label.getText());
        System.out.println();

        // The format: "Totalbeløp etter perioden: kr x"
        assertTrue(label.getText().equals("Totalbeløp etter perioden: kr " + sum ));
    }

    /**
     * Testing whether the savings calculator will calculate if the period is equal to 0
     * and if the period text field is set to "1", after the calculation. 
     */
    @Test
    @DisplayName("test a calculation with period equal to 0")
    public void testCalculation() {
        int sum = 1100;
        clickOn("#monthlyAmount").write("0");
        clickOn("#lumpAmount").write("1000");
        clickOn("#period").write("0");
        clickOn("#interestRate").write("10,0");
        clickOn("#findTotalAmount");

        Label label = lookup("#totalAmountText").query();

        // Format: "Totalbeløp etter perioden: kr x"
        assertTrue(label.getText().equals("Totalbeløp etter perioden: kr " + sum ));

        TextField period = lookup("#period").query();

        assertTrue(period.getText().equals("1"));
    }


    /**
     * Testing whether the text field "totalAmountText" is empty when the paramters are invalid.
     */
    @Test
    @DisplayName("test a calculation with invalid parameters")
    public void testInvalidCalculation() {
        clickOn("#monthlyAmount").write("0");
        clickOn("#lumpAmount").write("");
        clickOn("#period").write("0");
        clickOn("#interestRate").write("10,0");
        clickOn("#findTotalAmount");

        Label label1 = lookup("#totalAmountText").query();
        assertTrue(label1.getText().equals(""));

        clickOn("#lumpAmount").write("100");
        clickOn("#interestRate").write(",1");

        Label label2 = lookup("#totalAmountText").query();
        assertTrue(label2.getText().equals(""));
    } 


     /**
     * Testing whether calculator will return a text "Beløpet er utenfor rekkevidde!"
     * if the output is out of range.
     */
    @Test
    @DisplayName("test a calculation with invalid parameters")
    public void testCalculationWithOutputOutOfRange() {
        clickOn("#monthlyAmount").write("1000000");
        clickOn("#lumpAmount").write("100000000");
        clickOn("#period").write("20000000");
        clickOn("#interestRate").write("320,0");
        clickOn("#findTotalAmount");

        // Format: "Totalbeløp etter perioden: Beløpet er utenfor rekkevidde!"
        Label label1 = lookup("#totalAmountText").query();
        assertTrue(label1.getText().equals("Totalbeløp etter perioden: Beløpet er utenfor rekkevidde!"));

    } 
    
}

