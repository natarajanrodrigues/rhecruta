/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.upload;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CurriculumDAO;
import br.edu.ifpb.dac.rhecruta.core.mdb.StartMDB;
import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
            propertyValue = "javax.jms.Queue"
    )
    ,
    @ActivationConfigProperty(
            propertyName = "destinationLookup",
            propertyValue = "jms/dac/filesToUploadQueue"
    )
})
public class RequestUploadListener implements MessageListener {

    @EJB
    private DropboxBean dropboxBean;
    @EJB
    private CurriculumDAO curriculumDAO;

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
    
    @Override
    public void onMessage(Message message) {
        
        try {
            Curriculum file = message.getBody(Curriculum.class);
            upload(file);
        } catch (JMSException ex) {
            Logger.getLogger(RequestUploadListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void upload(Curriculum file) {
        curriculumDAO.upload(file);
    }


}
