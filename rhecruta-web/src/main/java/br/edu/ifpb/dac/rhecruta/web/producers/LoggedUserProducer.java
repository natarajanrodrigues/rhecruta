/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.producers;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pedro Arthur
 */

@ApplicationScoped
public class LoggedUserProducer {
    
    
    @Default
    @Dependent
    @Produces
    public User getLoggedUser() {
        //
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        //
        if(session == null)
            return null;
        //
        User loggedUser = (User) session.getAttribute("loggedUser");
        return loggedUser;
    }
}
