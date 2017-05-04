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
public enum InviteResult {
    
    ACCEPTED("Accepted"),
    DENIED("Denied"),
    NONE("Not responded");
    
    private final String description;

    private InviteResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
