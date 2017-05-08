/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.SystemEvaluationDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.SystemEvaluation;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EvaluationService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Pedro Arthur
 */

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "dac/rhecruta/systemEvaluationQueue")
})
public class SystemEvaluationListener implements MessageListener {
    
    @EJB
    private EvaluationService evaluationService;
    @EJB
    private SystemEvaluationDAO systemEvaluationDAO;
    @EJB
    private CandidateDAO candidateDAO;
    @EJB
    private OfferDAO offerDAO;
    
    private static final double MATCH_WEIGHT = 0.5;
    private static final double RANK_WEIGHT = 0.5;
    
    private double calculateScore(double match, double rank) {
        return (match*MATCH_WEIGHT) + (rank*RANK_WEIGHT);
    }

    @Override
    public void onMessage(Message message) {
        
        MapMessage mapMessage = (MapMessage) message;
        try {
            
            Long offerId = mapMessage.getLong("offer");
            Long candidateId = mapMessage.getLong("candidate");
            
            Offer offer = offerDAO.get(offerId);
            Candidate candidate = candidateDAO.get(candidateId);
            
            //getting match & rank
            double match = evaluationService.getMatch(offer, candidate);
            double rank = evaluationService.getRank(candidate);
            
            //calculating score
            double score = calculateScore(match, rank);
            
            //creating system_evaluation instance
            SystemEvaluation evaluation = new SystemEvaluation(score, candidate, offer);
            
            //persisting
            systemEvaluationDAO.save(evaluation);
            
        } catch (JMSException ex) {
            Logger.getLogger(SystemEvaluationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
