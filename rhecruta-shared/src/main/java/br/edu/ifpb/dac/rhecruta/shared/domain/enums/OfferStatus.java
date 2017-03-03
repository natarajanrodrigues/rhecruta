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
    
    OPEN(1), CLOSED(2);
    
    int type;
    
    OfferStatus(int type) {
        this.type = type;
    }
}
