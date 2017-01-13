package ch.makery.address.view;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label emailLabel;

    // Référence à l'application Main
    private MainApp mainApp;

    /**
     * Le constructeur.
     * Le constructeur est appelé avant la méthode initialize ().
     */
    public PersonOverviewController() {
    }

    /**
     * Initialise la classe du contrôleur. Cette méthode est automatiquement appelée
     * Après le chargement du fichier fxml.
     */
    @FXML
    private void initialize() {
        // Initialiser la table de personnes avec les deux colonnes.
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        // Effacer les détails de la personne.
        showPersonDetails(null);

        // Écoutez les changements de sélection et affichez les détails de la personne lorsqu'ils sont modifiés.
        personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Est appelé par l'application principale pour donner une référence à lui-même.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Ajouter des données de liste observables à la table
        personTable.setItems(mainApp.getPersonData());
    }
    /**
    * Remplit tous les champs de texte pour afficher les détails de la personne.
    * Si la personne spécifiée est nulle, tous les champs de texte sont effacés.
    *
    * @param person la personne ou null
    */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Remplissez les étiquettes avec des informations de l'objet personne.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            emailLabel.setText(person.getEmail());

            // Convertir l'anniversaire en une chaîne!
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        
        }   else  {
            // Person est nul, supprimez tout le texte.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
            emailLabel.setText("");
            }
    }
    /**
    * Appelé lorsque l'utilisateur clique sur le bouton Supprimer.
    */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        }   
        else  {
            // Rien sélectionné.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    /**
    * Appelé lorsque l'utilisateur clique sur le nouveau bouton. Ouvre une boîte de dialogue pour modifier
    * Détails pour une nouvelle personne.
    */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
    * Appelé lorsque l'utilisateur clique sur le bouton d'édition. Ouvre une boîte de dialogue pour modifier
    * Détails pour la personne sélectionnée.
    */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } 
        else {
            // Rien selectionné.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
}