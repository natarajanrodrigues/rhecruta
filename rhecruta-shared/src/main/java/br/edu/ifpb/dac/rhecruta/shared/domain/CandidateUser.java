/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain;

/**
 *
 * @author Pedro Arthur
 */
public class CandidateUser extends User {
    
    private String linkedInUrl;
    private String githubUrl;

    public CandidateUser(String linkedInUrl, String githubUrl, String cpf, String firstname, String lastname, Address address, Credentials credentials) {
        super(cpf, firstname, lastname, address, credentials);
        this.linkedInUrl = linkedInUrl;
        this.githubUrl = githubUrl;
    }
    
    public CandidateUser() {
        
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    @Override
    public String toString() {
        return "CandidateUser{" + "linkedInUrl=" + linkedInUrl + ", githubUrl=" + githubUrl + '}';
    }
}
