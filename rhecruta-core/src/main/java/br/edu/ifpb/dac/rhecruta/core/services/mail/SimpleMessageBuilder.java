/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.mail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Pedro Arthur
 */
public class SimpleMessageBuilder {
    
    private final List<Address> destiny;
    private String subject;
    private String text;
    private Session session;
    private String from;
    private LocalDateTime sentDateTime;
    
    public SimpleMessageBuilder() {
        this.destiny = new ArrayList<>();
    }
    
    public SimpleMessageBuilder addDestiny(String to) throws AddressException {
        Address address = new InternetAddress(to);
        this.destiny.add(address);
        return this;
    }
    
    public SimpleMessageBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
    public SimpleMessageBuilder setText(String text) {
        this.text = text;
        return this;
    }
    
    public SimpleMessageBuilder setSession(Session session) {
        this.session = session;
        return this;
    }
    
    public SimpleMessageBuilder setFrom(String from) {
        this.from = from;
        return this;
    }
    
    public SimpleMessageBuilder setSentDateTime(LocalDateTime sendDateTime) {
        this.sentDateTime = sendDateTime;
        return this;
    }
    
    public Message build() throws MessagingException {
        //
        Message message = new MimeMessage(session);
        Address[] arrayDestiny = new Address[destiny.size()];
        message.setRecipients(Message.RecipientType.TO, destiny.toArray(arrayDestiny));
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(new InternetAddress(from));
        if(this.sentDateTime == null) {
            this.sentDateTime = LocalDateTime.now();
        }
        message.setSentDate(toDate(this.sentDateTime));
        
        return message;
    }
    
    private Date toDate(LocalDateTime dateTime) {
        if(this.sentDateTime == null) return null;
        Date messageDate = Date.from(this.sentDateTime
                .atZone(ZoneId.systemDefault()).toInstant());
        return messageDate;
    }
}
