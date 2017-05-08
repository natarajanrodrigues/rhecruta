/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.SystemEvaluation;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface SystemEvaluationDAO {
    
    void save(SystemEvaluation systemEvaluation);
    List<SystemEvaluation> listByOffer(Offer offer);
    SystemEvaluation getByOfferAndCandidate(Offer offer, Candidate candidate);
}
