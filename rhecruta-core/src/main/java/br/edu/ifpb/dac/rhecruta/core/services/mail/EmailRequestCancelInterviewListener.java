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
 * @author Pedro Arthur
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
    ,
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/dac/cancelInterview")
})
public class EmailRequestCancelInterviewListener implements MessageListener {

    @EJB
    private EmailSender sender;
    
    @Override
    public void onMessage(Message message) {
        try {

            Enterview enterview = message.getBody(Enterview.class);
            Email createEmail = createEmail(enterview);
            sender.send(createEmail);
        } catch (JMSException ex) {
            Logger.getLogger(EmailRequestCancelInterviewListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Email createEmail(Enterview enterview) {
        String message;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");;

        StringBuilder sb = new StringBuilder();
        sb.append("Olá ").append(enterview.getCandidate().getFirstname()).append(", infelizmente a entrevista da oferta "
                + "descrita abaixo foi cancelada. Aguarde por novas instruções.:\n")
                .append(enterview.getOffer().prettyString());

        message = sb.toString();

        Email email = new Email();
        email.setFrom("rhecrutapp@gmail.com");
        email.setTo(enterview.getCandidate().getUser().getCredentials().getEmail());
        email.setRequestedDate(LocalDateTime.now());
        String subject = "Entrevista cancelada";
        email.setSubject(subject);
        email.setText(message);
        return email;
    }
}
