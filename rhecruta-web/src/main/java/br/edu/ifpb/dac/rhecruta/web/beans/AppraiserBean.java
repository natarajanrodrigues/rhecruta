/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur && Natarajan
 */

@Named
@RequestScoped
public class AppraiserBean {
    
    @Inject
    private AdministratorService administratorService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private User loggedUser;
    
    private Administrator administrator = new Administrator();
    private User user = new User();
    private Credentials credentials = new Credentials();
    
    private Administrator administratorUpdateble = new Administrator();

        
    @PostConstruct
    private void postConstruct() {
        
        if (loggedUser != null) 
            administratorUpdateble = administratorService.getByUser(getLoggedAdministrator().getUser());
        System.out.println("Construi o AdministratorBean!");
    }
    
    @PreDestroy
    private void preDestroy() {
        System.out.println("Destrui o AdministratorBean!");
    }
    
    public String saveAdministrator() {
        //
        this.user.setCredentials(credentials);
        this.administrator.setUser(user);
        //
        this.administratorService.save(administrator);
        
        System.out.println("[AdministratorBean: "+administrator+"]");
        
        return "result_request_register.xhtml?faces-redirect=true";
    }
    
    public String updateAdministrator() {
        
        System.out.println("[aaaaaaaaaa Updatable: "+administratorUpdateble+"]");
        
        administratorService.update(administratorUpdateble);
        
        FacesContext.getCurrentInstance().addMessage("password-form", new FacesMessage("Successfull updated"));
        
        return null;
    }
    
    public Administrator getLoggedAdministrator() {
        System.out.println("User: "+loggedUser);
        if(loggedUser != null) {
            Administrator logged = this.administratorService.getByUser(loggedUser);
            System.out.println("Administrator: "+logged);
            return logged;
        } return null;
    }
    
    public List<Administrator> getAdministratorsToApprove() {
        return administratorService.listAdministratorsToApprove();
    }
    
    public String respondRequest(Administrator administrator, boolean approve) {
        administratorService.respondRequest(administrator, approve);
        return null;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Administrator getAdministratorUpdateble() {
        return this.administratorUpdateble;
    }

    public void setAdministratorUpdateble(Administrator administratorUpdateble) {
        this.administratorUpdateble = administratorUpdateble;
    }
    
}
