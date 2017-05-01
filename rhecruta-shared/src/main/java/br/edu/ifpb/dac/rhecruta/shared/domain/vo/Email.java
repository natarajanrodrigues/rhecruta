/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Pedro Arthur
 */

public class Email implements Serializable {
    
    private String text;
    private String subject;
    private String from;
    private String to;
    private LocalDateTime requestedDate;

    public Email(String text, String subject, String from, String to) {
        this.text = text;
        this.subject = subject;
        this.from = from;
        this.to = to;
    }
    
    public Email() {
    } 

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    @Override
    public String toString() {
        return "Email{" + "text=" + text + ", subject=" + subject + ", from=" + from + ", to=" + to + ", requestedDate=" + requestedDate + '}';
    }
}
