/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface EnterviewService {
    
    void save(Enterview enterview);
    List<Enterview> listAll();
    void evaluate(Enterview enterview, Double score);
    void cancel(Enterview enterview);
    List<Enterview> listByOffer(Offer offer);
    Enterview getByOfferAnCandidate(Offer offer, Candidate candidate);
    
    List<Enterview> listByCandidate(Candidate candidate);
    List<Enterview> listByAppraiser(Administrator administrator);
    List<Enterview> listByManager(Administrator administrator);
    boolean hasInterview(Offer offer, Candidate candidate);
    
}
