/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
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
    List<Offer> getByAdministrator(Administrator admnistrator);
    Offer getById(Long offerId);
    List<Offer> getByType(OfferType offerTeyp);
    boolean isSubscribed(Long offerId, Candidate candidate);

    public List<Offer> getByCandidate(Candidate candidate);

    public boolean isAttached(Long offerId, Long administratorId);
    
}
