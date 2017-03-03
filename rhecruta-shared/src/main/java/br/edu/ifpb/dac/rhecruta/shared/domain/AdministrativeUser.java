/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain;

import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;

/**
 *
 * @author Pedro Arthur
 */
public class AdministrativeUser extends User {
    
    private Role role;

    public AdministrativeUser(Role role, String cpf, String firstname, String lastname, Address address, Credentials credentials) {
        super(cpf, firstname, lastname, address, credentials);
        this.role = role;
    }

    public AdministrativeUser() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AdministrativeUser{" + "role=" + role + '}';
    }
}
