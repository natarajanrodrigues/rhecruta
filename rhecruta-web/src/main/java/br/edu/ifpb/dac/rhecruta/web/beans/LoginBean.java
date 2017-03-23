/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {
    
    private User loggedUser = new User();
    private Credentials credentials = new Credentials();
    
    public String signIn() {
        //this.loggedUser = loginService.signIn(credentials);
        switch(loggedUser.getRole()) {
            case CANDIDATE:
                //redirect to candidate/home.xhtml
                break;
            case APPRAISER:
                //redirect to appraiser/home.xhtml
                break;
            case MANAGER:
                //redirect to manager/home.xhtml
                break;
        }
        //FacesContext.addMessage("wrong email/password");
        return "index.xhtml?faces-redirect=true";
    }
    
    public String signOut() {
        this.loggedUser = new User();
        return "index.xhtml?faces-redirect=true";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
