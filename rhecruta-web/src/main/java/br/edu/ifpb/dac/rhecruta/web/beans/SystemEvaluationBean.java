/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.SystemEvaluation;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.SystemEvaluationService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class SystemEvaluationBean {
    
    @Inject
    private SystemEvaluationService systemEvaluationService;
    
    public double getSystemScoreByOffAndCandidate(Offer offer, Candidate candidate) {
        System.out.println("offer id: " + offer.getId() + ", candidate id: " + candidate.getId());
        SystemEvaluation evaluation = systemEvaluationService
                .getByOfferAndCandidate(candidate, offer);
        if(evaluation == null)
            return -1d;
        return evaluation.getScore();
    }
}
