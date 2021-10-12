package it1901;

import it1901.SavingsCalc;
import it1901.util.TextFieldFormatter;

import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SavingsCalcController {
    
    @FXML private TextField monthlyAmount;
    @FXML private TextField lumpAmount;
    @FXML private TextField period;
    @FXML private TextField interestRate;

    @FXML private Button findTotalAmount;

    @FXML private Label totalAmountText;

    @FXML 
    private void initialize() {
        setNumericOnlyTextFields();
        setText();
    }


    private void setText() {
        interestRate.setText("1");
        interestRate.setText("");
        totalAmountText.setText("");
    }

    
    private void setNumericOnlyTextFields() {     
        monthlyAmount.setTextFormatter(TextFieldFormatter.getNumberFormatter());
        lumpAmount.setTextFormatter(TextFieldFormatter.getNumberFormatter());
        period.setTextFormatter(TextFieldFormatter.getNumberFormatter());        
        interestRate.setTextFormatter(TextFieldFormatter.getDecimalFormatter());  
    }


    /**
     * Setting the findTotalAmount button disable if one of the textfields are empty
     * or if the interestRate text field is not on a decimal format.
     */
    @FXML
    private void findTotalAmountAble() {
        findTotalAmount.setDisable(monthlyAmount.getText().isEmpty() || lumpAmount.getText().isEmpty() || period.getText().isEmpty() || interestRate.getText().isEmpty() || !interestRateFieldValid());
    }

    
    /**
     * Calculates the total amount based on the values in text fields
     */
    private int calculateTotalAmount() {
        int ma = Integer.parseInt(monthlyAmount.getText());
        int la = Integer.parseInt(lumpAmount.getText());
        int p = Integer.parseInt(period.getText());
        double ir = interestRateFieldConverter();
        
        double calc = SavingsCalc.calculation(ma, la, p, ir);

        return (int) Math.round(calc);
    }


    @FXML
    private void handleFindTotalAmount(ActionEvent ev) {
        totalAmountText.setText("Totalbeløp etter perioden: kr " + calculateTotalAmount());
        
        if (Integer.parseInt(period.getText()) == 0)
            period.setText("1");
    }


    /**
     * Check if the text on the interestRate field is on decimal format.
     * 
     * @return true if a string containing only numbers has no comm in itself or if
     * a string containing numbers on both side of one comma between them. 
     */
    private boolean interestRateFieldValid() {
        String s = interestRate.getText();

        if (!s.contains(","))
            return true;

        List<String> list = Arrays.asList(s.split(","));
        
        if (s.substring(s.length() - 1).equals(",") )
            return false;

        return list.size() == 2 && list.get(0).length() != 0 && list.get(1).length() != 0;
    }


    /**
     * Convert a decimal with comma to a decimal with point (double).
     * 
     * @return a double value
     */
    private double interestRateFieldConverter() {
        String s = interestRate.getText();

        if (!s.contains(","))
            return Double.parseDouble(s + ".0");

        List<String> list = Arrays.asList(s.split(","));

        String string = list.get(0) + "." + list.get(1);

        return Double.parseDouble(string);
    } 

}
