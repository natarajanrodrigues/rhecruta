/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Address;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class CandidateBean {
    
    @Inject
    private CandidateService candidateService;
    
    private Candidate candidate = new Candidate();
    private User user = new User();
    private Credentials credentials = new Credentials();
    
    @Inject
    private LoginBean loginBean;
    
    public String saveCandidact() {
        this.user.setCredentials(credentials);
        this.user.setRole(Role.CANDIDATE);
        this.candidate.setUser(user);
        System.out.println(candidate);
        
        this.candidateService.save(candidate);
        this.candidate = new Candidate();
        
        return "index.xhtml?faces-redirect=true";
    }
    
    public Candidate getLoggedCandidate() {
        User loggedUser = this.loginBean.getLoggedUser();
        if(loggedUser != null) {
            Candidate loggedCandidate = candidateService.getByUser(loggedUser);
            return loggedCandidate;
        } return null;
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
}
