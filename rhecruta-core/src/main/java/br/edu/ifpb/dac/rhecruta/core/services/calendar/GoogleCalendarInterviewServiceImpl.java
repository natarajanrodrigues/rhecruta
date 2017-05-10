/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.calendar;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.EnterviewDAO;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.GoogleCalendarInterviewService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Natarajan
 */
@Remote(GoogleCalendarInterviewService.class)
@Stateless
public class GoogleCalendarInterviewServiceImpl implements GoogleCalendarInterviewService{

    private static final String CALENDAR_ID = "rhecrutapp@gmail.com";

    @EJB
    private EnterviewDAO enterviewDAO;
        
    private static Calendar getClientCalendar() throws FileNotFoundException, IOException, GeneralSecurityException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Rhecruta-chaves.json");
        
        
        GoogleCredential credential = GoogleCredential.fromStream(is)

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
    public String saveEventInterview(Event event) throws FileNotFoundException, IOException, GeneralSecurityException {
        Calendar client = getClientCalendar();
        Calendar.Calendars.Get rhcrutaCalendar = client.calendars().get(CALENDAR_ID);
        
        return client.events().insert(rhcrutaCalendar.getCalendarId(), event).execute().getId();
    }

    private static Event getEvent(Calendar clientCalendar, String eventId) throws IOException {
        return clientCalendar.events().get(CALENDAR_ID, eventId).execute();
    }

    
    @Override
    public void removeEvent(String eventId) throws IOException, FileNotFoundException, GeneralSecurityException {
        System.out.println("[deletando evento com id: ]" + eventId);
        getClientCalendar().events().delete(CALENDAR_ID, eventId).execute();
    }

    @Override
    public void updateEvent(String eventId, Event event) throws IOException, FileNotFoundException, GeneralSecurityException {
        getClientCalendar().events().update(CALENDAR_ID, eventId, event);
    }
    
    

}
