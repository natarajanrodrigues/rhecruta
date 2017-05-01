/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.mail;

import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import com.sun.mail.util.MailConnectException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 *
 * @author Pedro Arthur
 */
@Stateless
public class EmailSender {

    @Inject
    @JMSConnectionFactory("jms/dac/dacConnectionFactory")
    private JMSContext jmsContext;

    @Resource(lookup = "jms/dac/waitingEmailsQueue")
    private Queue waitingEmailsQueue;
    
    @Resource(lookup = "dac/rhecruta/javaMailSession")
    private Session session;
    
    public void send(Email email) {
        try {
            javax.mail.Message message = createMessage(email);
            System.out.println("[EmailSenderBean] sending email: "+email);
            Transport.send(message);
            System.out.println("[EmailSenderBean] successfully sent! email: "+email);
        } catch (MailConnectException ex) {
            System.out.println("[EmailSenderBean] Failed to send email "+email);
            sendToWaitingQueue(email);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private javax.mail.Message createMessage(Email email) throws MessagingException {

        javax.mail.Message mailMessage = new SimpleMessageBuilder()
                .setFrom(email.getFrom())
                .setSubject(email.getSubject())
                .setText(email.getText())
                .addDestiny(email.getTo())
                .setSession(this.session)
                .setSentDateTime(LocalDateTime.now())
                .build();

        return mailMessage;
    }

    @Schedule(second = "*/10", minute = "*", hour = "*")
    private void trySendWaitingEmails() {
        System.out.println("[EmailSenderBean] trying re-send failed emails...");
        JMSConsumer consumer = jmsContext.createConsumer(waitingEmailsQueue);
        while (true) {
            Email email = consumer.receiveBody(Email.class, 1000);
            if (email == null) break;
            send(email);
        }
    }

    private void sendToWaitingQueue(Email email) {
        JMSProducer producer = jmsContext.createProducer();
        ObjectMessage objMessage = jmsContext.createObjectMessage(email);
        System.out.println("[EmailSenderBean] Sending to waitingEmailsQueue...");
        producer.send(waitingEmailsQueue, objMessage);
    }
}
