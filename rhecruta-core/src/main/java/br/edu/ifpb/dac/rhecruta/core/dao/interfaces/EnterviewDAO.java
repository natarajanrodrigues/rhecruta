/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.dto.EnterviewSystemEvaluationDTO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface EnterviewDAO  {
    
    List<Enterview> listAll();
    Enterview findById(Long id);
    List<Enterview> listByAppraiser(Administrator appraiser);
    void delete(Enterview enterview);
    public List<Enterview> listByOffer(Offer offer);
    Enterview getByOfferAndCandidate(Long offerId, Long candidateId);
    List<Enterview> listByCandidate(Candidate candidate);
    List<Enterview> listByManager(Administrator administrator);
    Long save(Enterview obj);
    void update(Enterview obj);
    Long countInterviewsByOffer(Offer offer);
    List<EnterviewSystemEvaluationDTO> getResultByOfferOrderedByScore(Long offerId);
}
