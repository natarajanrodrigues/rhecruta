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
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/dac/newInterview")
})
public class EmailRequestCreateInterviewListener implements MessageListener {

    @EJB
    private EmailSender sender;

    @Override
    public void onMessage(Message message) {
        try {

            Enterview enterview = message.getBody(Enterview.class);
            Email createEmail = createEmail(enterview);
            sender.send(createEmail);
        } catch (JMSException ex) {
            Logger.getLogger(EmailRequestCreateInterviewListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Email createEmail(Enterview enterview) {
        String message;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");;

        StringBuilder sb = new StringBuilder();
        sb.append("Olá ").append(enterview.getCandidate().getFirstname()).append(" estamos felizes em confirmar "
                + "que foi marcada uma entrevista para você. Veja os detalhes da oferta e da entrevista:\n")
                .append(enterview.getOffer().prettyString())
                .append("Data/Hora Início: ").append(dtf.format(enterview.getStart())).append("\n")
                .append("Data/Hora Final: ").append(dtf.format(enterview.getEnd()));

        message = sb.toString();

        Email email = new Email();
        email.setFrom("rhecrutapp@gmail.com");
        email.setTo(enterview.getCandidate().getUser().getCredentials().getEmail());
        email.setRequestedDate(LocalDateTime.now());
        String subject = "Entrevista marcada";
        email.setSubject(subject);
        email.setText(message);
        return email;
    }
}
