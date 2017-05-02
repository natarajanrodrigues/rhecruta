/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferStatus;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
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
public class OfferBean {
    
    @Inject
    private AdministratorService administratorService;
    
    @Inject
    private OfferService offerService;

    @Inject
    private User loggedUser;
    
    private Administrator loggedAdministrator;
    private boolean creatingOffer;  
    private Offer offer = new Offer();
    private List<Offer> administratorOffers;

        
    @PostConstruct
    private void init() {
        if (loggedUser != null)  {
            loggedAdministrator = administratorService.getByUser(getLoggedAdministrator().getUser());
            this.administratorOffers = offerService.getByAdministrator(loggedAdministrator);
        }
        this.offer = new Offer();
        this.offer.setStatus(OfferStatus.OPEN);
        this.creatingOffer = false;
        System.out.println("Construiu o OfferBean!");
    }
    
    @PreDestroy
    private void preDestroy() {
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
        offer.addAdministrator(loggedAdministrator);
        
        offerService.save(offer);
        
        init();
        
        return null;
    }    

    public List<Offer> getAdministratorOffers() {
        return this.administratorOffers;
    }
    
    public String removeOffer(Offer offer) {
        offerService.remove(offer);
        init();
        return null;
    }
    
    
}
