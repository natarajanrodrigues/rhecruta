/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewHandlerService;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

/**
 *
 * @author natarajan
 */
@Remote(EnterviewHandlerService.class)
@Stateless
public class EnterviewHandlerServiceImpl implements EnterviewHandlerService {
  
    @Inject
    @JMSConnectionFactory(value = "jms/dac/dacConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "jms/dac/newInterview")
    private Topic topicCriating;
    
    @Resource(lookup = "jms/dac/cancelInterview")
    private Topic topicDeleting;
    
    @Override
    public void handleCriation(Enterview enterview) {
        
        System.out.println("[EnterviewHandlerServiceImpl] Handling Interview Added: " + enterview);
        
        JMSProducer producer = context.createProducer();
        ObjectMessage message = context.createObjectMessage(enterview);
        producer.send(topicCriating, message);
    }
    
    @Override
    public void handleDeletion(Enterview enterview) {
        
        System.out.println("[EnterviewHandlerServiceImpl] Handling Interview Removed: " + enterview);
        
        JMSProducer producer = context.createProducer();
        ObjectMessage message = context.createObjectMessage(enterview);
        producer.send(topicDeleting, message);
    }

    
}
