/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.LoginService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {
    
    @Inject
    private LoginService loginService;
    
    private User loggedUser = new User();
    private Credentials credentials = new Credentials();
    
    public String signIn() {
        System.out.println("Credentials: "+credentials);
        this.loggedUser = loginService.signIn(this.credentials);
        if(this.loggedUser != null) {
            switch(loggedUser.getRole()) {
                case CANDIDATE:
                    return "candidate/home.xhtml";
                case APPRAISER:
                    return "appraiser/home.xhtml";
                case MANAGER:
                    return "manager/home.xhtml?faces-redirect=true";
            }
        }
        //FacesContext.addMessage("wrong email/password");
        return "index.xhtml?faces-redirect=true";
    }
    
    public String signOut() {
        System.out.println("fez Logout");
        this.loggedUser = new User();
        return "/index.xhtml?faces-redirect=true";
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
