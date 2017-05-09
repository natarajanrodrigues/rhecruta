/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.upload;

import br.edu.ifpb.dac.rhecruta.core.mdb.StartMDB;
import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

/**
 *
 * @author Pedro Arthur
 */

@Stateless
public class RequestUploadBean {
    
    @EJB
    private DropboxBean dropbox;
    
    @Inject
    @JMSConnectionFactory(value = "jms/dac/dacConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "jms/dac/filesToUploadQueue")
    private Queue queue;
    
    @EJB
    private StartMDB sb;

    @PostConstruct
    public void init() {
        System.out.println("[INIT MDB COMEÇOU: NewOfferListener]");
        if (!sb.isInit()) {
            System.out.println("NUNCA DEVE ACONTECER");
        }
        System.out.println("[INICIOU MDB TERMINOU: NewOfferListener]");
    }
    
    public void upload(Curriculum file) {
        
        JMSProducer producer = context.createProducer();
        ObjectMessage message = context.createObjectMessage(file);
        producer.send(queue, message);
    }
    
}
