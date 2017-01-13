package ch.makery.address.util;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptateur (pour JAXB) pour convertir entre LocalDate et l'ISO 8601
 * Représentation de chaîne de la date telle que '2012-12-03'.
 * 
 * @author Marco Jakob
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}