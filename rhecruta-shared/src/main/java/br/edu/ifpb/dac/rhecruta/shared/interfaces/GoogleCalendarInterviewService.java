/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import com.google.api.services.calendar.model.Event;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author Pedro Arthur
 */
public interface GoogleCalendarInterviewService {
    
    String saveEventInterview(Event event) throws FileNotFoundException, IOException, GeneralSecurityException;
    void removeEvent(String eventId) throws IOException, FileNotFoundException, GeneralSecurityException ;
    void updateEvent(String eventId, Event event) throws IOException, FileNotFoundException, GeneralSecurityException;    
    
}
