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
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJBException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur && Natarajan
 */

@Named
@ConversationScoped
public class AppraiserBean implements Serializable {
    
    @Inject
    private Conversation conversation;
    
    @Inject
    private AdministratorService administratorService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private User loggedUser;
    
    private Administrator loggedAppraiser;
        
    @Inject
    private EnterviewService interviewService;
    
    private Enterview enterview;
    private double score;
    
    @PostConstruct
    private void postConstruct() {
        initConversation();
        this.loggedAppraiser = getLoggedAdministrator();
        System.out.println("[AppraiserBean] constructed!");
    }
    
    @PreDestroy
    private void preDestroy() {
        endConversation();
        System.out.println("[AppraiserBean] erased!");
    }
    
    public String startEvaluation(Enterview enterview) {
        System.out.println("[AppraiserBean.startEvaluation(enterview)] enterview: "+enterview);
        this.enterview = enterview;
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
    
    public List<Enterview> getInterviews(){
        return interviewService.listByAppraiser(getLoggedAdministrator());
    }

    public Enterview getEnterview() {
        return enterview;
    }

    public void setEnterview(Enterview enterview) {
        this.enterview = enterview;
    }
    
    public String evaluate() {
        try {
            System.out.println("[AppraiserBean.evaluate()] evaluating...");
            interviewService.evaluate(this.enterview, score);
            addMessage("evaluateMsg", 
                    createMessage("The interview was evaluated with "+score+" successfully!", 
                    FacesMessage.SEVERITY_INFO));
        } catch (EJBException ex) {
            addMessage("evaluateMsg", 
                    createMessage(ex.getCausedByException().getMessage(),
                    FacesMessage.SEVERITY_INFO));
        }
        return null;
    }
    
    private FacesMessage createMessage(String text, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }

    private void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    private void initConversation() {
        if(this.conversation.isTransient()) {
            this.conversation.begin();
        }
    }
    
    private void endConversation() {
        if(!this.conversation.isTransient()) {
            this.conversation.end();
        }
    }
    
    
}
