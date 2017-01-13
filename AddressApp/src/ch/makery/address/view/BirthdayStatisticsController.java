package ch.makery.address.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import ch.makery.address.model.Person;

/**
 * Le contrôleur pour la vue statistiques anniversaire.
 * 
 * @author Marco Jakob
 */
public class BirthdayStatisticsController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private final ObservableList<String> monthNames = FXCollections.observableArrayList();

    /**
     * Initialise la classe du contrôleur. Cette méthode est automatiquement appelée
     * Après le chargement du fichier fxml.
     */
    @FXML
    private void initialize() {
        // Obtenir un tableau avec les noms de mois anglais.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convertissez-le en une liste et ajoutez-le à notre ObservableList de mois.
        monthNames.addAll(Arrays.asList(months));

        // Affectez les noms de mois en tant que catégories pour l'axe horizontal.
        xAxis.setCategories(monthNames);
    }

    /**
     * Définit les personnes pour lesquelles les statistiques doivent être affichées.
     * 
     * @param persons
     */
    public void setPersonData(List<Person> persons) {
        // Compter le nombre de personnes ayant leur anniversaire dans un mois donné.
        int[] monthCounter = new int[12];
        persons.stream().map((p) -> p.getBirthday().getMonthValue() - 1).forEachOrdered((month) -> {
            monthCounter[month]++;
        });

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Créez un objet XYChart.Data pour chaque mois. Ajoutez-le à la série
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }
    
}