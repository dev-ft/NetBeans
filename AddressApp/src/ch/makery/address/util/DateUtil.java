package ch.makery.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Fonctions d'aide pour le traitement des dates.
 *
 * @author Marco Jakob
 */
public class DateUtil {

    /** Le modèle de date utilisé pour la conversion. Changez comme vous le souhaitez. */
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    /** Le formatage de date. */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Renvoie la date donnée sous la forme d'une chaîne bien formatée. Les définitions ci-dessus
     * {@link DateUtil # DATE_PATTERN} est utilisé.
     *
     * @param date la date à retourner sous forme de chaîne
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Convertit une chaîne au format {@link DateUtil # DATE_PATTERN}
     * Vers un objet {@link LocalDate}.
     *
     * Renvoie null si la chaîne ne peut pas être convertie.
     *
     * @param dateString la date comme String
     * @return l'objet date ou null s'il ne peut pas être converti
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Vérifie si la chaîne est une date valide.
     *
     * @param dateString
     * @return true si la chaîne est une date valide
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
}