/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Pedro Arthur
 */

@Embeddable
public class InvitePK implements Serializable {
    
    private Long offer;
    private Long invited;
    private Long inviter;

    public InvitePK() {
        //
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.offer);
        hash = 29 * hash + Objects.hashCode(this.invited);
        hash = 29 * hash + Objects.hashCode(this.inviter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvitePK other = (InvitePK) obj;
        if (!Objects.equals(this.offer, other.offer)) {
            return false;
        }
        if (!Objects.equals(this.invited, other.invited)) {
            return false;
        }
        if (!Objects.equals(this.inviter, other.inviter)) {
            return false;
        }
        return true;
    }

    public Long getOfferId() {
        return offer;
    }

    public void setOfferId(Long offerId) {
        this.offer = offerId;
    }

    public Long getInvitedId() {
        return invited;
    }

    public void setInvitedId(Long invitedId) {
        this.invited = invitedId;
    }

    public Long getInviterId() {
        return inviter;
    }

    public void setInviterId(Long inviterId) {
        this.inviter = inviterId;
    }

    @Override
    public String toString() {
        return "InvitePK{" + "offerId=" + offer + ", invitedId=" + invited + ", inviterId=" + inviter + '}';
    }
}
