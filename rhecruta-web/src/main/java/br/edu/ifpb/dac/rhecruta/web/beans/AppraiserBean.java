/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
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
    
    private Administrator loggedAppraiser;
        
    @Inject
    private EnterviewService interviewService;
    
    @PostConstruct
    private void postConstruct() {
        
        this.loggedAppraiser = getLoggedAdministrator();
        System.out.println("Construi o AdministratorBean!");
    }
    
    @PreDestroy
    private void preDestroy() {
        System.out.println("Destrui o AdministratorBean!");
    }
    
    
    public Administrator getLoggedAdministrator() {
        System.out.println("User: "+loggedUser);
        if(loggedUser != null) {
            Administrator logged = this.administratorService.getByUser(loggedUser);
            System.out.println("Administrator: "+logged);
            return logged;
        } return null;
    }
    
    public List<Enterview> getInterviews(){
        return interviewService.listByAppraiser(getLoggedAdministrator());
    }
    
    
}
