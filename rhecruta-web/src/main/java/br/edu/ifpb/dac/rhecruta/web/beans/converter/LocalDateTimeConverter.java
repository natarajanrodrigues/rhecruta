/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Pedro Arthur
 */

@FacesConverter("localDateTimeConverter")
public class LocalDateTimeConverter implements Converter {
    
    private final String pattern = "dd/MM/yyyy HH:mm";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.isEmpty()) return null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = LocalDateTime.parse(value, dtf);
        return dateTime;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) return "";
        LocalDateTime dateTime = (LocalDateTime) value;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(dtf);
    }
    
}
