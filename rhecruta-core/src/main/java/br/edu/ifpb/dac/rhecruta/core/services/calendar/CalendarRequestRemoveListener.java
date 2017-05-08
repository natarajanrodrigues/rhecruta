/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.calendar;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.GoogleCalendarInterviewService;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/dac/cancelInterview")
})
public class CalendarRequestRemoveListener implements MessageListener {

    @EJB
    private GoogleCalendarInterviewService calendarService;
    
    @EJB
    private EnterviewService interviewService;

    @Override
    public void onMessage(Message message) {
        try {
            
            Enterview interview = message.getBody(Enterview.class);
            
            System.out.println("[CalendarRequestListener - retirando interview do calendário]: " + interview.getId() + "\n");
            System.out.println("[Id do evento a ser removido]: " + interview.getCalendarId()+ "\n");
            calendarService.removeEvent(interview.getCalendarId());
            
        } catch (JMSException | IOException | GeneralSecurityException ex) {
            Logger.getLogger(CalendarRequestRemoveListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
