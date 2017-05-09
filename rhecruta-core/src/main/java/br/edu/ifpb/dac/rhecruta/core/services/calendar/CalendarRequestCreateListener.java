/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.calendar;

import br.edu.ifpb.dac.rhecruta.core.mdb.StartMDB;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.GoogleCalendarInterviewService;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/dac/newInterview")
})
public class CalendarRequestCreateListener implements MessageListener {

    @EJB
    private GoogleCalendarInterviewService calendarService;
    
    @EJB
    private EnterviewService interviewService;

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
            
            Enterview interview = message.getBody(Enterview.class);
            
            System.out.println("[CalendarRequestListener - enviando interview para o calendário]: " + interview + "\n");
            
            Event createdEvent = createEvent(interview);
            String idInterviewCalendar = calendarService.saveEventInterview(createdEvent);
            interview.setCalendarId(idInterviewCalendar);
            interviewService.update(interview);
            
        } catch (JMSException | IOException | GeneralSecurityException ex) {
            Logger.getLogger(CalendarRequestCreateListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Event createEvent(Enterview enterview) {
        Event event = new Event()
                .setSummary("Entrevista da oferta: " + enterview.getOffer().getId())
                .setDescription(prettyStringInterview(enterview));

        DateTime startDateTime = new DateTime(convertFromLocalDateTime(enterview.getStart()));
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Brazil/East");
        event.setStart(start);

        DateTime endDateTime = new DateTime(convertFromLocalDateTime(enterview.getEnd()));
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Brazil/East");
        event.setEnd(end);
        return event;
    }
    
    static String prettyStringInterview(Enterview enterview){
        StringBuilder sb = new StringBuilder();
        String recuo = "\n";
        sb
                .append("== Oferta == \n")
                .append("Descrição: ").append(enterview.getOffer().getDescription()).append(recuo)
                .append(recuo)
                .append("== Candidato == \n")
                .append("Nome: ")
                .append(enterview.getCandidate().getFirstname())
                .append(" ").append(enterview.getCandidate().getLastname());
                
        return sb.toString();
    }
    
    static String convertFromLocalDateTime(LocalDateTime ldt) {
        LocalDateTime plusHours = ldt.plusHours(3);
        String dateTimeFormated = plusHours.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dataConvertida = DateTime.parseRfc3339(dateTimeFormated).toString();
        System.out.println("DATA CONVERTIDA: " + dataConvertida);
        return dataConvertida;
    }
}
