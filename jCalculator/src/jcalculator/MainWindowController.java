package jcalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *Controlller of the main window
 * @author gilles
 */
public class MainWindowController {

    private Float previousValue;
    private String currentOperation;

    @FXML
    private TextField resultTF;

    /**
     * Initialize variables
     */
    public MainWindowController() {
        previousValue = 0.0f;
        currentOperation = "";
    }

    /**
     * Used by all digits buttons
     * @param event 
     */
    @FXML
    private void actionBDigit(ActionEvent event) {
        // Get textfield content
        String currentValue = resultTF.getText();

        // Concatenate 0 to the value
        Button btn = (Button) event.getSource();
        currentValue += btn.getText();

        // Send back the string to the textfield
        resultTF.setText(currentValue);
    }

    /**
     * Used by the dot button
     * @param event 
     */
    @FXML
    private void actionBDot(ActionEvent event) {
        // Get textfield content
        String currentValue = resultTF.getText();

        // indexOf:
        // ".99" -> retourne 0
        // "1.99" -> retoune 1
        // "11.99"-> retourne 2
        if (currentValue.indexOf('.') == -1) {
            // Concatenate . to the value
            currentValue += ".";

            // Send back the string to the textfield
            resultTF.setText(currentValue);
        }
    }

    /**
     * Handling of the Minus button
     * @param event 
     */
    @FXML
    private void actionBMinus(ActionEvent event) {

        // Get textfield content
        String currentValue = resultTF.getText();

        if (previousValue == 0) {
            previousValue = Float.parseFloat(currentValue);
        } 

        // Save operation
        currentOperation = "-";
        // Clear the TF
        resultTF.setText("");

    }

     /**
     * Handling of the plus button
     * @param event 
     */
    @FXML
    private void actionBPlus(ActionEvent event) {

        // Get textfield content
        String currentValue = resultTF.getText();

        if (previousValue== 0) {
            previousValue = Float.parseFloat(currentValue);
        } 
        
        // Save operation
        currentOperation = "+";
        // Clear the TF
        resultTF.setText("");

    }

     /**
     * Handling of the Equal button
     * @param event 
     */
    @FXML
    private void actionBEqual(ActionEvent event) {

        if (currentOperation.compareTo("+") == 0) {
            previousValue += Float.parseFloat(resultTF.getText());
        } else if (currentOperation.compareTo("-") == 0) {
            previousValue -= Float.parseFloat(resultTF.getText());
        }

        resultTF.setText(String.valueOf(previousValue));
        currentOperation="";
    }

}
