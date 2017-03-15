/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Address;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Pedro Arthur
 */
@Entity
public class Candidate implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String cpf;
    
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    
    @Embedded
    private Address address;
    
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "system_user_id", nullable = false)
    private User user;
    
    @Column(name = "linkedin_url")
    private String linkedInUrl;
    @Column(name = "github_url")
    private String githubUrl;

    public Candidate(Long id, User user, String linkedInUrl, String githubUrl) {
        this.id = id;
        this.user = user;
        this.linkedInUrl = linkedInUrl;
        this.githubUrl = githubUrl;
    }
    
    public Candidate() {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "Candidate{" + "id=" + id + ", cpf=" + cpf + ", firstname=" + firstname + ", lastname=" + lastname + ", address=" + address + ", user=" + user + ", linkedInUrl=" + linkedInUrl + ", githubUrl=" + githubUrl + '}';
    }
}
