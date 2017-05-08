/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.SystemEvaluationDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.SystemEvaluation;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.SystemEvaluationService;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */
@Remote(SystemEvaluationService.class)
@Stateless
public class SystemEvaluationServiceImpl implements SystemEvaluationService {

    @EJB
    private SystemEvaluationDAO systemEvaluationDAO;

    @Override
    public SystemEvaluation getByOfferAndCandidate(Candidate candidate, Offer offer) {
        System.out.println("[SystemEvaluationServiceImpl"
                + ".getByOfferAndCandidate()] candidate: " + candidate +
                " && offer: " + offer);
        return systemEvaluationDAO.getByOfferAndCandidate(offer, candidate);
    }
}
