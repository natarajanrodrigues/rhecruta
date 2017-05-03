/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface EnterviewService {
    
    void save(Enterview enterview);
    List<Enterview> listAll();
    void evaluate(Enterview enterview, Double score);
    
}
