/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Pedro Arthur
 */
public class InvitePK implements Serializable {
    
    private Long offerId;
    private Long invitedId;
    private Long inviterId;

    public InvitePK() {
        //
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.offerId);
        hash = 29 * hash + Objects.hashCode(this.invitedId);
        hash = 29 * hash + Objects.hashCode(this.inviterId);
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
        if (!Objects.equals(this.offerId, other.offerId)) {
            return false;
        }
        if (!Objects.equals(this.invitedId, other.invitedId)) {
            return false;
        }
        if (!Objects.equals(this.inviterId, other.inviterId)) {
            return false;
        }
        return true;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(Long invitedId) {
        this.invitedId = invitedId;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    @Override
    public String toString() {
        return "InvitePK{" + "offerId=" + offerId + ", invitedId=" + invitedId + ", inviterId=" + inviterId + '}';
    }
}
