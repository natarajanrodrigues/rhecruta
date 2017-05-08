/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
@Named(value = "offerDetailsBean")
@ConversationScoped
public class OfferDetailsBean implements Serializable {

    @Inject
    private Conversation conversation;

    @Inject
    private AdministratorService administratorService;

    @Inject
    private OfferService offerService;

    @Inject
    private User loggedUser;

    private Administrator loggedAdministrator;

    private Offer offer;

    private String skill = "";

    @PostConstruct
    private void init() {
        this.skill = "";
        initConversation();
        System.out.println("Construiu o OfferDetailsBean!");
    }

    @PreDestroy
    private void preDestroy() {
        endConversation();
        System.out.println("Destrui o OfferDetailsBean!");
    }

    public Administrator getLoggedAdministrator() {
        System.out.println("User: " + loggedUser);
        if (loggedUser != null) {
            Administrator logged = this.administratorService.getByUser(loggedUser);
            System.out.println("Administrator: " + logged);
            return logged;
        }
        return null;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String offerDetails(Offer offer) {
        this.offer = offerService.getById(offer.getId());
        for (String s : offer.getSkills()) {
            System.out.println(s);
        }
        return "/manager/offer/offer_details.xhtml?faces-redirect=true";
    }

    public String addSkill() {
        this.offer.addSkill(this.skill);
        this.offerService.update(offer);
        this.skill = "";
        return null;
    }

    public String removeSkill(String aSkill) {
        this.offer.removeSkill(aSkill);
        this.offerService.update(offer);
        this.skill = "";
        return null;
    }

    public List<Administrator> getAllAppraisers() {
        return administratorService.getAllAdministratorsByRole(Role.APPRAISER);
    }

    public String addAppraiserToOffer(Administrator administrator) {
        this.offer.setAppraiser(administrator);
        this.offerService.update(this.offer);
        return null;
    }

    public String removeAppraiserToOffer() {

        this.offer.setAppraiser(null);
        this.offerService.update(offer);
        return null;

//        try {
//
//            if (this.offer.getSkills().size() > 0) {
//                throw new EJBException("Can't remove. There are scheduled interviews ");
//            } else {
//                this.offer.setAppraiser(null);
//                this.offerService.update(offer);
//            }
//        } catch (EJBException ex) {
//            addMessage("offerMsg", //antes "enterviewMsg"
//                    createMessage(ex.getCausedByException().getMessage(),
//                            FacesMessage.SEVERITY_ERROR));
//            return null;
//        }
//
//        return null;
    }

    private FacesMessage createMessage(String text, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }

    private void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }

    public boolean isSelectedOfferInvite() {
        OfferType type = offer.getType();
        return type.equals(OfferType.INVITE);
    }

//    public boolean isAdmin(Long adminId) {
//        System.out.println("ADM ID: " + adminId);
//        return this.offerService.isAttached(offer.getId(), adminId);
//    }
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

    public List<Candidate> getAllCandidates() {
        return offerService.getSubscribers(this.offer);
    }

    public String scheduleInterview(Candidate candidate) {
        return null;
    }
}
