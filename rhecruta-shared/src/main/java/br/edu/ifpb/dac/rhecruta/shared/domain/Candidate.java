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
public class Candidate {
    
    private Long id;
    private User user;
    private String linkedInUrl;
    private String githubUrl;
    private boolean approved;

    public Candidate(Long id, User user, String linkedInUrl, String githubUrl, boolean approved) {
        this.id = id;
        this.user = user;
        this.linkedInUrl = linkedInUrl;
        this.githubUrl = githubUrl;
        this.approved = approved;
    }
    
    public Candidate() {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
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

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
    

    @Override
    public String toString() {
        return "Candidate{" + "id=" + id + ", user=" + user + ", linkedInUrl=" + linkedInUrl + ", githubUrl=" + githubUrl + '}';
    }
}
