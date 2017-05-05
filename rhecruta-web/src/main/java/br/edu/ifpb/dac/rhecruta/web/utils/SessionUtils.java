/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pedro Arthur
 */
public class SessionUtils {
    
    public static HttpSession getSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(create);
    }
    
    public static void invalidate() {
        getSession(false).invalidate();
    }
}
