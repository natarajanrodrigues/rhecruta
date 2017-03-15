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
public enum OfferType {
    
    OPEN(1, "Pública"), INVITE(2, "Por convite");
    
    private final int id;
    private final String description;
    
    OfferType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public static OfferType parse(int id) {    
        for(OfferType offerType : OfferType.values()) {
            if(offerType.getId() == id) {
                return offerType;
            }
        } return null;
    }
}
