/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.dto.EnterviewSystemEvaluationDTO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface OfferService {
    
    void save(Offer offer);
    void remove(Offer offer);
    void update(Offer offer);
    Offer getById(Long offerId);
    List<Offer> listAll();
    void subscribe(Candidate candidate, Offer offer);
    void unsubscribe(Candidate candidate, Offer offer);
//    List<Offer> getByAdministrator(Administrator admnistrator);
    List<Offer> getByManager(Administrator manager);
    List<Offer> getByAppraiser(Administrator appraiser);
    List<Offer> getByType(OfferType offerType);
    boolean isSubscribed(Long offerId, Candidate candidate);
    List<Offer> getByCandidate(Candidate candidate);
    Long countScheduledInterview(Offer offer);
//    boolean isAttached(Long offerId, Long administratorId);
    List<Candidate> getSubscribers(Offer offer);
    List<Offer> getMonthOffers();
    Object[] getMonthOffersByLanguage();
    Object[] getMonthCandidatesPerVacancyBySkill();
    
    List<EnterviewSystemEvaluationDTO> getResultOrderedByScore(Offer offer);
    
    
}
