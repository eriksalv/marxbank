package marxbank.util;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

public class TextFieldFormatter {

    /**
     * returns a TextFormatter that only allows numbers in text fields.
     * @return if the change matches the criterea of the 
     * number formatter, return change, else return null
    */    
    public static TextFormatter<String> getNumberFormatter() {
        UnaryOperator<Change> filter = change -> {
            String text = change.getText();
        
            if (text.matches("[0-9]*")) {
                return change;
            }
        
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter); 
        return textFormatter;
    }

    /**
     * returns a TextFormatter that only allows numbers and commas
     * (decimals) in text fields.
     * @return if the change matches the criterea of the 
     * decimal formatter, return change, else return null
    */  
    public static TextFormatter<String> getDecimalFormatter() {
        UnaryOperator<Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*,*[0-9]*")) { 
                return change;
            }
            return null;
        };
        
        TextFormatter<String> textFormatter = new TextFormatter<>(filter); 
        return textFormatter;
    }

}
