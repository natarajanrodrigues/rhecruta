/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
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
public class CandidateBean {
    
    @Inject
    private CandidateService candidateService;
    
    @Inject
    private OfferService offerService;
    
    @Inject
    private User loggedUser;
    
    private Candidate candidate = new Candidate();
    private User user = new User();
    private Credentials credentials = new Credentials();
    
    
    
    @PostConstruct
    private void postConstruct() {
        System.out.println("Construi o CandidateBean!");
    }
    
    @PreDestroy
    private void preDestroy() {
        System.out.println("Destrui o CandidateBean!");
    }
    
    public String saveCandidact() {
        System.out.println("Salvando candidato...");
        this.user.setCredentials(credentials);
        this.user.setRole(Role.CANDIDATE);
        this.candidate.setUser(user);
        System.out.println(candidate);
        
        this.candidateService.save(candidate);
        this.candidate = new Candidate();
        
        return "result_request_register.xhtml?faces-redirect=true";
    }
    
    public Candidate getLoggedCandidate() {
        
        if(loggedUser != null) {//está dando um erro aqui 
            Candidate loggedCandidate = candidateService.getByUser(loggedUser);
            return loggedCandidate;
        } return null;
    }
    
    public List<Candidate> getCandidatesToApprove() {
        return candidateService.listCandidatesToApprove();
    }
    
    public String respondRequest(Candidate candidate, boolean approve) {
        this.candidateService.respondRequest(candidate, approve);
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
        
        offer.subscribe(getLoggedCandidate());
        offerService.update(offer);
        return null;
    }
    
    public String unsubscribe(Offer offer) {
        
        offer.unsubscribe(getLoggedCandidate());
        offerService.update(offer);
        return null;
    }

    public boolean isLoggedCandidateSubscribed(Long id) {
        
        System.out.println("Candidate id:" + getLoggedCandidate().getId());
        return this.offerService.isSubscribed(id, getLoggedCandidate());
    }
    
    public List<Offer> getCandidateOffers(){
        return this.offerService.getByCandidate(getLoggedCandidate());
    }
    
}
