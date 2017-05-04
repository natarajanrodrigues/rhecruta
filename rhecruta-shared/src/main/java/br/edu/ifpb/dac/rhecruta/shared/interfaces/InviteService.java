/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Invite;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.InviteResult;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface InviteService {
    
    void save(Invite invite);
    void delete(Invite invite);
    void answer(Invite invite, InviteResult result);
    List<Invite> listByManager(Administrator manager);
    List<Invite> listByCandidate(Candidate candidate);
    List<Invite> listByResult(InviteResult result);
    boolean hasPendentOrAcceptedInvite(Candidate candidate, Offer offer);
}
