/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface OfferService {
    
    void save(Offer offer);
    void remove(Offer offer);
    void update(Offer offer);
    List<Offer> listAll();
}
