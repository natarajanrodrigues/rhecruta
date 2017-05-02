/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.LoginService;
import br.edu.ifpb.dac.rhecruta.web.utils.SessionUtils;
import java.io.Serializable;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pedro Arthur
 */
@Named
@RequestScoped
public class LoginBean implements Serializable {
    
    @Inject
    private LoginService loginService;
    
    @Inject
    private User loggedUser;
    
    private Credentials credentials = new Credentials();
    
    public String signIn() {
        System.out.println("Credentials: "+credentials);
        
        HttpSession session = SessionUtils.getSession(true);
        
        try {
            User loggedUser = loginService.signIn(this.credentials);
            
            session.setAttribute("loggedUser", loggedUser);
            System.out.println("achou usuario");
            switch(loggedUser.getRole()) {
                case CANDIDATE:
                    return "candidate/home.xhtml?faces-redirect=true";
                case APPRAISER:
                    return "appraiser/home.xhtml?faces-redirect=true";
                case MANAGER:
                    return "manager/home.xhtml?faces-redirect=true";
                default:
                    System.out.println("Sem Role!");
                    return "index.xhtml?faces-redirect=true";
            }
            
        } catch (EJBException ex) {
            System.out.println("Entrou na exceção: "+ex.getCausedByException().getMessage());
            FacesMessage message = new FacesMessage(ex.getCausedByException().getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("loginError", message);
            return null;
        }  
    }
    
    public String signOut() {
        HttpSession session = SessionUtils.getSession(false);
        session.setAttribute("loggedUser", null);
        return "/index.xhtml?faces-redirect=true";
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
