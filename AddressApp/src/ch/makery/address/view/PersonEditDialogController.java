package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/**
 * Boîte de dialogue pour modifier les détails d'une personne.
 *
 * @author Marco Jakob
 */
public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField emailField;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initialise la classe du contrôleur. Cette méthode est automatiquement appelée
     * Après le chargement du fichier fxml.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Définit la phase de cette boîte de dialogue.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Définit la personne à éditer dans la boîte de dialogue.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
        emailField.setText(person.getEmail());
    }

    /**
     * Renvoie true si l'utilisateur a cliqué sur OK, false sinon.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Appelé lorsque l'utilisateur clique sur OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            person.setEmail(emailField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Appelée lorsque l'utilisateur clique sur Annuler.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Valide l'entrée de l'utilisateur dans les champs de texte.
     *
     * @retour vrai si l'entrée est valide
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // Essaye d'analyser le code postal dans un int
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No valid email!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Montre le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
}