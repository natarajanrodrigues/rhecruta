/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.enums;

/**
 *
 * @author Pedro Arthur
 */
public enum Role {
    
    CANDIDATE(1, "Candidato"), MANAGER(2, "Gerente"), APPRAISER(3, "Avaliador");
    
    private final int id;
    private final String description;
    
    Role(int type, String description) {
        this.id = type;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public static Role parse(int id) {   
        for(Role role : Role.values()) {
            if(role.getId() == id) {
                return role;
            }
        } return null;
    }
}
