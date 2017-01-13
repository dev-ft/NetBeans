package ch.makery.address.view;

import ch.makery.address.MainApp;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;


/**     
 * Le contrôleur pour la disposition des racines. La disposition des racines fournit les
 * Présentation d'application contenant une barre de menu et un espace où d'autres JavaFX
 * Éléments peuvent être placés.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

    // Référence à l'application principale
    private MainApp mainApp;

    /**
     * Est appelé par l'application principale pour donner une référence à lui-même.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Crée un carnet d'adresses vide.
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }
    /**
    * Ouvre les statistiques d'anniversaire.
    */
    @FXML
    private void handleShowBirthdayStatistics() {
    mainApp.showBirthdayStatistics();
    }

    /**
     * Ouvre FileChooser pour permettre à l'utilisateur de sélectionner un carnet d'adresses à charger.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Définir le filtre d'extension
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue Enregistrer fichier
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Enregistre le fichier dans le fichier de personne actuellement ouvert. Si il n'y a pas
     * Ouvrir le fichier, la boîte de dialogue "Enregistrer sous" est affichée.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Ouvre FileChooser pour permettre à l'utilisateur de sélectionner un fichier à enregistrer.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Définir le filtre d'extension
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue Enregistrer fichier
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Assurez-vous qu'il a la bonne extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Ouvre une boîte de dialogue.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

        alert.showAndWait();
    }

    /**
     * ferme l'application
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}