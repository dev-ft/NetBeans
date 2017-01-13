package  ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class MainApp extends Application {
    
    // ... APRES LES AUTRES VARIABLES ...

    /**
     * Les données comme une liste observable de personnes.
     */
    private final ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
    // AJout de données
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    /**
     * Retourne les données comme une liste observable de personnes. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    // ... LE RESTE DE LA CLASSE ...

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        this.primaryStage.setMinWidth(640);
        this.primaryStage.setMinHeight(520);

        // Donne l'îcone de l'application.
        this.primaryStage.getIcons().add(new Image("file:home/charlie/Images/Address_Book.png"));

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Iinitialisation
     */
    public void initRootLayout() {
        try {
            // Charger la disposition des racines depuis le fichier fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Affiche la scène contenant la disposition des racines
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Donne l'accès du contrôleur à l'application principale.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        }   
        catch (IOException e) {
        }

        // Essaye de charger le fichier de la dernière personne ouverte.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Affiche l'aperçu de la personne dans la disposition racine.
     */
    public void showPersonOverview() {
        try {
            // Charge une personne.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Donne l'aperçu des personnes au centre de la disposition des racines.
            rootLayout.setCenter(personOverview);
            
            // Donne l'accès du contrôleur à l'application principale.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        
        } catch (IOException e) {
        }
    }

    /**
     * Retourne la fenêtre principal.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    /**
    Ouvre une boîte de dialogue pour modifier les détails de la personne spécifiée. Si l'utilisateur
    * Clique sur OK, les modifications sont enregistrées dans l'objet person fournie et true
    * Est retourné.
    *
    * @param personne l'objet personne à modifier
    * @return true si l'utilisateur a cliqué sur OK, false sinon.
    */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Chargez le fichier fxml et créez une nouvelle étape pour la boîte de dialogue contextuelle.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Créer la boîte de dialogue stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Place la personne dans le contrôleur.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Affiche la boîte de dialogue et attendre que l'utilisateur la ferme
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }   
        catch (IOException e) {
            return false;
        }
    }
    /**
    * Renvoie la préférence du fichier personne, c'est-à-dire le fichier qui a été ouvert pour la dernière fois.
    * La préférence est lue dans le registre spécifique au système d'exploitation. Si aucun
    * La préférence peut être trouvée, null est retourné.
    * 
    * @return
    */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        }   else  {
            return null;
        }
    }

    /**
    * 
    * Définit le chemin du fichier actuellement chargé. Le chemin est persisté dans
    * Le registre spécifique de l'OS.
    *
    * @param file le fichier ou null pour supprimer le chemin d'accès
    */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Mise à jour du titre de la fenêtre
            primaryStage.setTitle("AddressApp - " + file.getName());
        } 
        else  {
            prefs.remove("filePath");

            // Mise à jour du titre de la fenêtre
            primaryStage.setTitle("AddressApp");
        }
    }
    /**
    * Charge des données personnelles à partir du fichier spécifié. Les données personnelles courantes
    * Est remis, remplacé.
    * 
    * @param file
    */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Lecture de XML à partir du fichier et unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Enregistre le chemin d'accès au Registre.
            setPersonFilePath(file);
        }   
        catch (JAXBException e) { // catches n'importe quelle exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
    * Enregistre les données de la personne courante dans le fichier spécifié.
    * 
    * @param file
    */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context;
            context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Enveloppe nos données personnelles.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling et enregistrement XML dans le fichier.
            m.marshal(wrapper, file);

            // Enregistrez le chemin d'accès au Registre.
            setPersonFilePath(file);
        }   
        catch (JAXBException e) { // catches n'importe quelle exception.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    /**
    * Ouvre une boîte de dialogue pour afficher les statistiques d'anniversaire.
    */
    public void showBirthdayStatistics() {
        try {
            // Charge le fichier fxml et créez une nouvelle étape pour le popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Met les personnes dans le contrôleur.
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();

        } 
        catch (IOException e) {
        }
    }
}
