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
public enum SystemStatus {
    
    OPEN(1, "Open"), CLOSED(2, "Closed");
    
    private final int id;
    private final String description;
    
    SystemStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static SystemStatus parse(int id) {    
        for(SystemStatus offerStatus : SystemStatus.values()) {
            if(offerStatus.getId() == id) {
                return offerStatus;
            }
        } return null;
    }
    
    
}
