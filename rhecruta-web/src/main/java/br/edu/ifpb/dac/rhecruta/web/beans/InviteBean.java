/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Invite;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.InviteResult;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.InviteService;
import java.util.List;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class InviteBean {
    
    @Inject
    private InviteService inviteService;
    
    public String invite(Offer offer, Candidate candidate, Administrator manager) {
        
        try {
            
            Invite invite = new Invite();
            invite.setOffer(offer);
            invite.setInvited(candidate);
            invite.setInviter(manager);
            invite.setResult(InviteResult.NONE);

            inviteService.save(invite);
            
            addMessage("inviteMsg", 
                    createMessage("O convite foi realizado com sucesso!", 
                    FacesMessage.SEVERITY_INFO));
            
//            return "/manager/invites.xhtml?faces-redirect=true";
            return null;
        
        } catch (EJBException ex) {
            addMessage("inviteMsg", 
                    createMessage(ex
                            .getCausedByException().getMessage(), 
                    FacesMessage.SEVERITY_INFO));
            
            return null;
        }
    }
    
    public List<Invite> getInvitesByManager(Administrator manager) {
        return inviteService.listByManager(manager);
    }
    
    public List<Invite> getInvitesByCandidates(Candidate candidate) {
        return inviteService.listByCandidate(candidate);
    }
    
    public String answerInvite(Invite invite, boolean accepted) {
        try {
            inviteService.answer(invite, accepted ? 
                    InviteResult.ACCEPTED : InviteResult.DENIED);
            addMessage("inviteMsg", 
                    createMessage("The invite was responded successfully!!", 
                    FacesMessage.SEVERITY_INFO));
            
        } catch (EJBException ex) {
            addMessage("inviteMsg", 
                    createMessage(ex.getCausedByException().getMessage(), 
                    FacesMessage.SEVERITY_ERROR));
        }
        
        return null;
    }
    
    public boolean hasInvite(Candidate cand, Offer offer) {
        return inviteService.hasInvite(cand, offer);
    }
    
    public boolean isInviteAnswered(Invite invite) {
        return !invite.getResult().equals(InviteResult.NONE);
    }
    
    public boolean isInviteAccepted(Invite invite) {
        return invite.getResult().equals(InviteResult.ACCEPTED);
    }
    
    
    private FacesMessage createMessage(String text, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }

    private void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
}
