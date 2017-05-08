/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import java.io.Serializable;


/**
 *
 * @author natarajan
 */
public class SimpleUser implements Serializable{

    private Long id;
    private String githubAccount;
    private String linkedinAccount;
    
    public SimpleUser() {
    }
    
    public SimpleUser(Candidate candidate) {
        
        this.githubAccount = candidate.getGithubUrl();
        this.linkedinAccount = candidate.getLinkedInUrl();
    }
    
    public SimpleUser(String githubAccount, String linkedinAccount) {
        this.githubAccount = githubAccount;
        this.linkedinAccount = linkedinAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGithubAccount() {
        return githubAccount;
    }

    public void setGithubAccount(String githubAccount) {
        this.githubAccount = githubAccount;
    }

    public String getLinkedinAccount() {
        return linkedinAccount;
    }

    public void setLinkedinAccount(String linkedinAccount) {
        this.linkedinAccount = linkedinAccount;
    }

    @Override
    public String toString() {
        return "SimpleUser{" + "id=" + id + ", githubAccount=" + githubAccount + ", linkedinAccount=" + linkedinAccount + '}';
    }
    
}
