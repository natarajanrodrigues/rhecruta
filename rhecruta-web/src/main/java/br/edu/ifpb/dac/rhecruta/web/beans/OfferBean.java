/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.SystemStatus;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur && Natarajan
 */

@Named
@ConversationScoped
public class OfferBean implements Serializable{
    
    @Inject
    private Conversation conversation;
    
    @Inject
    private AdministratorService administratorService;
    
    @Inject
    private OfferService offerService;
    
    @Inject
    private EnterviewService interviewService;

    @Inject
    private User loggedUser;
    
    private Administrator loggedAdministrator;
    private boolean creatingOffer;  
    private Offer offer = new Offer();
    private List<Offer> administratorOffers;

    private String skill = "";
    
    @PostConstruct
    private void init() {
        initConversation();
        if (loggedUser != null)  {
            loggedAdministrator = administratorService.getByUser(getLoggedAdministrator().getUser());
            this.administratorOffers = offerService.getByManager(loggedAdministrator);
        }
        this.offer = new Offer();
        this.offer.setStatus(SystemStatus.OPEN);
        this.creatingOffer = false;
        System.out.println("Construiu o OfferBean!");
    }
    
    @PreDestroy
    private void preDestroy() {
        endConversation();
        System.out.println("Destrui o OfferBean!");
    }
    
    public Administrator getLoggedAdministrator() {
        System.out.println("User: "+loggedUser);
        if(loggedUser != null) {
            Administrator logged = this.administratorService.getByUser(loggedUser);
            System.out.println("Administrator: "+logged);
            return logged;
        } return null;
    }
    
    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
    
    public String addSkill() {
        this.offer.addSkill(this.skill);
        this.skill = "";
        return null;
    }
    
    public String removeSkill(String aSkill) {
        this.offer.removeSkill(aSkill);
        this.skill = "";
        return null;
    }
    
    public void initConversation() {
        if (conversation.isTransient()) {
            this.conversation.begin();
        }
    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            this.conversation.end();
        }
    }
    
    
    public boolean isCreatingOffer() {
        return creatingOffer;
    }
    
    public String initCreateOffer(){
        this.creatingOffer = true;
        return null;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
    
    public String save(){
        offer.setManager(getLoggedAdministrator());
        
        offerService.save(offer);
        
        init();
//        endConversation();
//        initConversation();
//        init();
//        return null;
        return "/manager/offer/offer.xhtml?faces-redirect=true";
    }    
    
    public String offerDetails(Offer offer) {
        this.offer = offerService.getById(offer.getId());
        
//        System.out.println("OFFER " + offer);
//        System.out.println("AQUI AS SKILLS");
//        for (String s : offer.getSkills()) {
//            System.out.println(s);
//        }
        return "/manager/offer/offer_details.xhtml?faces-redirect=true";
    }
    
    

    public List<Offer> getAdministratorOffers() {
        return this.administratorOffers;
    }
    
    public String removeOffer(Offer offer) {
        offerService.remove(offer);
        init();
        return null;
    }
    
//    public Enterview getEnterviewByOfferAndCandidate(Candidate candidate){
//        return interviewService.getByOfferAnCandidate(this.offer, candidate);
//    }
    
    
}
