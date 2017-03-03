/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain;

/**
 *
 * @author Pedro Arthur
 */
public class Invite {
    
    private Offer offer;
    private CandidateUser invited;
    private AdministrativeUser inviter;
    private boolean accepted;

    public Invite(Offer offer, CandidateUser invited, AdministrativeUser inviter, boolean accepted) {
        this.offer = offer;
        this.invited = invited;
        this.inviter = inviter;
        this.accepted = accepted;
    }

    public Invite() {
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public CandidateUser getInvited() {
        return invited;
    }

    public void setInvited(CandidateUser invited) {
        this.invited = invited;
    }

    public AdministrativeUser getInviter() {
        return inviter;
    }

    public void setInviter(AdministrativeUser inviter) {
        this.inviter = inviter;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Invite{" + "offer=" + offer + ", invited=" + invited + ", inviter=" + inviter + ", accepted=" + accepted + '}';
    }
}
