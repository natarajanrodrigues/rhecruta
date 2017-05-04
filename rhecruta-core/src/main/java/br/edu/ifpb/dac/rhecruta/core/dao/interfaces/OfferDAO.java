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
    List<Offer> getByManager(Administrator manager);
    List<Offer> getByAppraiser(Administrator appraiser);
    Offer getById(Long offerId);
    List<Offer> getByType(OfferType offerTeyp);
    boolean isSubscribed(Long offerId, Candidate candidate);
//    boolean isAttached(Long offerId, Long administratorId);
    List<Offer> getByCandidate(Candidate candidate);
    List<Candidate> getSubscribers(Offer offer);

    public List<Offer> getMonthOffers();
    Object[] getMonthOffersByLanguage();
    Object[] getMonthCandidatesPerVacancyBySkill();
    
}
