/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Queue;

/**
 *
 * @author Pedro Arthur
 */

@Stateless
public class SystemEvaluationSenderMessage {
    
    @Inject
    @JMSConnectionFactory("jms/dac/dacConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup = "dac/rhecruta/systemEvaluationQueue")
    private Queue systemEvaluationQueue;
    
    public void sendToSystemEvaluationQueue(Offer offer, Candidate candidate) {
        
        MapMessage message = context.createMapMessage();
        try {
            message.setLong("offer", offer.getId());
            message.setLong("candidate", candidate.getId());
            
            JMSProducer producer = context.createProducer();
            producer.send(systemEvaluationQueue, message);
            
        } catch (JMSException ex) {
            Logger.getLogger(InviteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
