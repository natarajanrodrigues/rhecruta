/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.EnterviewDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.GoogleCalendarInterviewService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author natarajan
 */
@Remote(GoogleCalendarInterviewService.class)
@Stateless
public class GoogleCalendarInterviewServiceImpl implements GoogleCalendarInterviewService{

    private static final String CALENDAR_ID = "rhecrutapp@gmail.com";

    @EJB
    private EnterviewDAO enterviewDAO;
        
    private static Calendar getClientCalendar() throws FileNotFoundException, IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("src/Rhecruta-chaves.json"))
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/calendar"));

        Calendar client = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                new JacksonFactory(),
                credential)
                .setApplicationName("Rhecruta")
                .build();

        return client;
    }

    @Override
    public String saveEventInterview(Enterview enterview) throws FileNotFoundException, IOException, GeneralSecurityException {
        Calendar client = getClientCalendar();
        
        Event createdEvent = createEvent(enterview);
        Calendar.Calendars.Get rhcrutaCalendar = client.calendars().get(CALENDAR_ID);
        Event returnedEvent = client.events().insert(rhcrutaCalendar.getCalendarId(), createdEvent).execute();
        return returnedEvent.getId();
    }

    private static Event getEvent(Calendar clientCalendar, String eventId) throws IOException {
        return clientCalendar.events().get(CALENDAR_ID, eventId).execute();
    }

    @Override
    public Event createEvent(Enterview enterview) {
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

    private static String convertFromLocalDateTime(LocalDateTime ldt) {
        String dateTimeFormated = ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return DateTime.parseRfc3339(dateTimeFormated).toString();
    }

    @Override
    public void removeEvent(String eventId) throws IOException, FileNotFoundException, GeneralSecurityException {
        getClientCalendar().events().delete(CALENDAR_ID, eventId);
    }

    @Override
    public void updateEvent(String eventId, Event event) throws IOException, FileNotFoundException, GeneralSecurityException {
        getClientCalendar().events().update(CALENDAR_ID, eventId, event);
    }
    
    private static String prettyStringInterview(Enterview enterview){
        StringBuilder sb = new StringBuilder();
        String recuo = "\n";
        sb
                .append("== Oferta == \n")
                .append("Descrição: ").append(enterview.getOffer().getDescription()).append(recuo)
                .append(recuo)
                .append("== Candidato == \n")
                .append("Nome: ").append(enterview.getCandidate().getFirstname() + " " + enterview.getCandidate().getLastname());
                
        return sb.toString();
    }

}
