/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.GithubRepository;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EvaluationService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Pedro Arthur
 */

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
            propertyName = "destinationType", 
            propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(
            propertyName = "destinationLookup", 
            propertyValue = "jms/dac/rhecruta/newOfferQueue")
})
public class NewOfferListener implements MessageListener {
    
    @EJB
    private EmailRequester emailRequester;
    @EJB
    private CandidateDAO candidateDAO;
    @EJB
    private OfferDAO offerDAO;
    @EJB
    private EvaluationService evaluationService;
    

    @Override
    public void onMessage(Message message) {
        
        try {
            String strOfferId = message.getBody(String.class);
            Long offerId = Long.valueOf(strOfferId);
            Offer offer = offerDAO.getById(offerId);
            
            List<Candidate> candidates = candidateDAO.listApprovedCandidates();
            for(Candidate candidate : candidates) {
                if(evaluationService.getMatch(offer, candidate) > 0) {
                    Email email = createEmail(candidate, offer);
                    emailRequester.send(email);
                }
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(NewOfferListener.class.getName()).log(Level
                    .SEVERE, null, ex);
        }
    }
    
    private Email createEmail(Candidate candidate, Offer offer) {
         Email email = new Email();
         email.setFrom("rhecrutapp@gmail.com");
         email.setRequestedDate(LocalDateTime.now());
         email.setSubject("Rhecruta - Nova vaga!");
         email.setTo(candidate.getUser().getCredentials().getEmail());
         
         StringBuilder sb = new StringBuilder();
         String name = candidate.getFirstname() + ' '
                 + candidate.getLastname();
         sb.append("Olá, ").append(name).append("!")
                .append("\n")
                .append("Achamos que você possa gostar de uma nova oferta de vagas!\n")
                .append("Detalhes da vaga: \n")
                .append("Descrição: \n")
                .append(offer.getDescription())
                .append("Habilidades requeridas: \n");
                for(String skill : offer.getSkills()) {
                    sb.append(skill).append("\n");
                }
                sb.append("Nº Vagas: ").append(offer.getVacancies());
                
                sb.append("\n")
                  .append("Não perca mais tempo, corra no Rhecruta e se inscreva na vaga! \n");
         
         email.setText(sb.toString());
         
         return email;
    }
    
    
}
