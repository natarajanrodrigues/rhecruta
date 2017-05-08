/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.InviteDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Invite;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.InviteResult;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.InviteService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@Remote(InviteService.class)
@Stateless
public class InviteServiceImpl implements InviteService {
    
    @EJB
    private InviteDAO inviteDAO;
    @EJB
    private CandidateDAO candidateDAO;
    @EJB
    private OfferService offerService;
    @EJB
    private SystemEvaluationSenderMessage systemEvaluationSender;
    

    @Override
    public void save(Invite invite) {
        invite.setDateTime(LocalDateTime.now());
        inviteDAO.save(invite);
    }

    @Override
    public void delete(Invite invite) {
        inviteDAO.delete(invite);
    }

    @Override
    public List<Invite> listByManager(Administrator manager) {
        return inviteDAO.listByManager(manager);
    }

    @Override
    public List<Invite> listByCandidate(Candidate candidate) {
        return inviteDAO.listByCandidate(candidate);
    }

    @Override
    public List<Invite> listByResult(InviteResult result) {
        return inviteDAO.listByResult(result);
    }

    @Override
    public void answer(Invite invite, InviteResult result) {
        invite.setResult(result);
        inviteDAO.update(invite);
        
        if(result.equals(InviteResult.ACCEPTED)) {
        
            Offer offer = invite.getOffer();
            Candidate candidate = invite.getInvited();
            
            offerService.subscribe(candidate, offer);
        }
    }
    
    

    @Override
    public boolean hasInvite(Candidate candidate, Offer offer) {
        return inviteDAO.hasInvite(candidate.getId(), offer.getId());
    }
    
}
