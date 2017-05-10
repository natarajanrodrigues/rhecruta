/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.mail;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * @author Natarajan
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    ,
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/dac/newEmailsQueue")
})
public class EmailRequestNewsletterListener implements MessageListener {

    @EJB
    private EmailSender sender;

    @Override
    public void onMessage(Message message) {
        try {
            Email email = message.getBody(Email.class);
            sender.send(email);
        } catch (JMSException ex) {
            Logger.getLogger(EmailRequestNewsletterListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
