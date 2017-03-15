/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Pedro Arthur
 */
@Entity
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private Credentials credentials;
    
    @Column(name = "role_id")
    private int roleId;
    
    private boolean approved;

    public User(Credentials credentials, boolean approved) { 
        this.credentials = credentials;
        this.approved = approved;
    }
    
    public User() {
        this.approved = false;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return Role.parse(this.roleId);
    }

    public void setRole(Role role) {
        this.roleId = role.getId();
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", credentials=" + credentials + ", role=" + Role.parse(roleId) + ", approved=" + approved + '}';
    }
}
