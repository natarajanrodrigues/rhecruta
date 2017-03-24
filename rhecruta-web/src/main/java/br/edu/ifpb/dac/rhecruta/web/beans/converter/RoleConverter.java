/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans.converter;

import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Pedro Arthur
 */

@FacesConverter("RoleConverter")
public class RoleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("String to convert: "+value);
        Integer id = Integer.valueOf(value);
        return Role.parse(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Integer id = (Integer) value;
        return String.valueOf(id);
    } 
}
