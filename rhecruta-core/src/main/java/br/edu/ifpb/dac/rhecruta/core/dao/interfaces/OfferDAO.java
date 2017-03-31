/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface OfferDAO {
    
    void save(Offer offer);
    void update(Offer offer);
    void remove(Offer offer);
    Offer get(Long id);
    List<Offer> listAll();
}
