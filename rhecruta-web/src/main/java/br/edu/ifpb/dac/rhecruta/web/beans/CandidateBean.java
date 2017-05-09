/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.SimpleUser;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EvaluationService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJBException;
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
public class CandidateBean {
    
    @Inject
    private CandidateService candidateService;
    
    @Inject
    private OfferService offerService;
    
    @Inject
    private EnterviewService interviewService;
    
    @Inject
    private User loggedUser;
    
    @Inject
    private EvaluationService evaluationService;
    
    private Candidate candidate = new Candidate();
    private User user = new User();
    private Credentials credentials = new Credentials();
    
    private Candidate candidateUpgradable = new Candidate();
    
    
    @PostConstruct
    private void postConstruct() {
        if (loggedUser != null) 
            candidateUpgradable = candidateService.getByUser(loggedUser);
        System.out.println("Construi o CandidateBean!");
    }
    
    @PreDestroy 
    private void preDestroy() {
        System.out.println("Destrui o CandidateBean!");
    }
    
    public String saveCandidact() {
        
        try {
            System.out.println("Salvando candidato...");
            this.user.setCredentials(credentials);
            this.user.setRole(Role.CANDIDATE);
            this.candidate.setUser(user);
            System.out.println(candidate);

            this.candidateService.save(candidate);
            this.candidate = new Candidate();

            return "result_request_register.xhtml?faces-redirect=true";
            
        } catch (EJBException ex) {
            addMessage("adminRoleMsg",
                    createMessage(ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR));
            
            return null;
        }
        

    }
    
    private FacesMessage createMessage(String text, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }
    
    private void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
    
    public Candidate getLoggedCandidate() {
        
        if(loggedUser != null) {
            Candidate loggedCandidate = candidateService.getByUser(loggedUser);
            return loggedCandidate;
        } return null;
    }
    
    public List<Candidate> getCandidatesToApprove() {
        return candidateService.listCandidatesToApprove();
    }
    
    public List<Candidate> getApprovedCandidates() {
        List<Candidate> listApprovedCandidates = candidateService.listApprovedCandidates();
        
        Collections.sort(listApprovedCandidates, (c1, c2) -> 
                new Double(evaluationService.getRank(c1)).compareTo(
                        new Double(evaluationService.getRank(c2))) );
        
        Collections.reverse(listApprovedCandidates);
        
        return listApprovedCandidates;
    }
    
    public String respondRequest(Candidate candidate, boolean approve) {
        try {
            this.candidateService.respondRequest(candidate, approve);
        } catch(EJBException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("githuberror", message);
        }
        
        return null;
    }
    
    public Candidate getCandidact(User user) {
        return candidateService.getByUser(user);
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
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
 
    public List<Offer> getOpenOffers(){
        return offerService.getByType(OfferType.OPEN);
    }
    
    public String subscribe(Offer offer) {
        offerService.subscribe(getLoggedCandidate(), offer);
        return null;
    }
    
    public String unsubscribe(Offer offer) {
        
         offerService.unsubscribe(getLoggedCandidate(), offer);
        return null;
    }

    public boolean isLoggedCandidateSubscribed(Long id) {
        
        System.out.println("Candidate id:" + getLoggedCandidate().getId());
        return this.offerService.isSubscribed(id, getLoggedCandidate());
    }
    
    public List<Offer> getCandidateOffers(){
        return this.offerService.getByCandidate(getLoggedCandidate());
    }
    
    
    public String updateCandidate() {
        System.out.println("LOGGED Candidate: " + candidateUpgradable);
        candidateService.update(candidateUpgradable);
        
        FacesContext.getCurrentInstance().addMessage("password-form", new FacesMessage("Successfull updated"));
        
        return null;
    }

    public Candidate getCandidateUpgradable() {
        return candidateUpgradable;
    }
    
    public List<Enterview> getInterviews(){
        return interviewService.listByCandidate(getLoggedCandidate());
    }
    
    public String isValidCandidateOnSuggestions(Candidate candidate) {
        List<SimpleUser> resultList = evaluationService.searchSimpleUserWithOr(candidate);
        
        switch(resultList.size()) {
            case 1: {
                return "Exists/Correct";
            }
            case 0: {
                return "Don't exists yet";
            }
            default: return "Conflict with existing";
        }
        
    }
    
    public double rank(Candidate candidate) {
        System.out.println("CAND " + candidate);
        return evaluationService.getRank(candidate);
    }
    
}
