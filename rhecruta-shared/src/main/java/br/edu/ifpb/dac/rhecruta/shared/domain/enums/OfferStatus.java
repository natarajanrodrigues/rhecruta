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
public enum OfferStatus {
    
    OPEN(1, "Aberta"), CLOSED(2, "Fechada");
    
    private final int id;
    private final String description;
    
    OfferStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static OfferStatus parse(int id) {    
        for(OfferStatus offerStatus : OfferStatus.values()) {
            if(offerStatus.getId() == id) {
                return offerStatus;
            }
        } return null;
    }
    
    
}
