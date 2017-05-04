/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import br.edu.ifpb.dac.rhecruta.shared.domain.enums.InviteResult;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Pedro Arthur
 * 
 */
@Entity
@IdClass(InvitePK.class)
public class Invite implements Serializable {
    
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "offer_id")
    private Offer offer;
    
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invited_id")
    private Candidate invited;
    
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inviter_id")
    private Administrator inviter;
    
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    
    @Enumerated(EnumType.STRING)
    private InviteResult result;

    public Invite(Offer offer, Candidate invited, Administrator inviter, LocalDateTime dateTime, InviteResult result) {
        this.offer = offer;
        this.invited = invited;
        this.inviter = inviter;
        this.dateTime = dateTime;
        this.result = result;
    }

    public Invite() {
        
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Candidate getInvited() {
        return invited;
    }

    public void setInvited(Candidate invited) {
        this.invited = invited;
    }

    public Administrator getInviter() {
        return inviter;
    }

    public void setInviter(Administrator inviter) {
        this.inviter = inviter;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public InviteResult getResult() {
        return result;
    }

    public void setResult(InviteResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Invite{" + "offer=" + offer + ", invited=" + invited + ", inviter=" + inviter + ", dateTime=" + dateTime + ", result=" + result + '}';
    }
}
