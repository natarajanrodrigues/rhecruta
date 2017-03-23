/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class AdministratorBean {
    
    @Inject
    private AdministratorService administratorService;
    
    private Administrator administrator = new Administrator();
    private User user = new User();
    private Credentials credentials = new Credentials();
    
    @Inject
    private LoginBean loginBean;
    
    public Role[] getRoles() {
        return new Role[] {Role.MANAGER, Role.APPRAISER};
    }
    
    public String saveAdministrator() {
        this.administratorService.save(administrator);
        return "index.xhtml?faces-redirect=true";
    }
    
    public Administrator getLoggedAdministrator() {
        User user = loginBean.getLoggedUser();
        System.out.println("User: "+user);
        if(user != null) {
            Administrator logged = this.administratorService.getByUser(user);
            System.out.println("Administrator: "+logged);
            return logged;
        } return null;
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
    
    
}