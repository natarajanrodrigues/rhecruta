/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;

/**
 *
 * @author natarajan
 */
public interface EnterviewHandlerService {
    
    void handleCriation(Enterview enterview);
    
    void handleDeletion(Enterview enterview);
    
}
