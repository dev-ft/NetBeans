/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcul.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Charlie
 */
public class MainWindowController implements Initializable {
    
    
    @FXML
    private TextField champGauche;
    @FXML
    private Button button;
    @FXML
    private TextField champDroit;
    @FXML
    private Label resultLabel;
    
    @FXML
    private void actionBtn(ActionEvent event) {
        Button appuye = (Button)event.getSource();
        resultLabel.setText(appuye.getText());
        
        /*
        Integer g = Integer.parseInt(champGauche.getText());
        
        Integer d = Integer.parseInt(champDroit.getText());
        Integer resultat = g + d;
        
        resultLabel.setText(String.valueOf(resultat));
*/
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnEnter(MouseEvent event) {
        button.setText("Ah !");
    }
    
}
