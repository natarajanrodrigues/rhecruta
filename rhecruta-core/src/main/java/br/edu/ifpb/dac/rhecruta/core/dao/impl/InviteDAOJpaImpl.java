/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.InviteDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Invite;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.InvitePK;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.InviteResult;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur
 */

@Local(InviteDAO.class)
@Stateless
public class InviteDAOJpaImpl implements InviteDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager em;

    @Override
    public void delete(Invite invite) {
        em.remove(invite);
    }

    @Override
    public Invite findById(InvitePK id) {
        return em.find(Invite.class, id);
    }

    @Override
    public List<Invite> listByManager(Administrator manager) {
        TypedQuery<Invite> query = em
                .createQuery("SELECT i FROM Invite i"
                        + " WHERE i.inviter = :manager"
                        + " ORDER BY i.dateTime DESC", Invite.class)
                .setParameter("manager", manager);
        return query.getResultList();
    }

    @Override
    public List<Invite> listByCandidate(Candidate candidate) {
        TypedQuery<Invite> query = em
                .createQuery("SELECT i FROM Invite i"
                        + " WHERE i.invited = :candidate"
                        + " ORDER BY i.dateTime DESC", Invite.class)
                .setParameter("manager", candidate);
        return query.getResultList();
    }

    @Override
    public List<Invite> listByResult(InviteResult result) {
        TypedQuery<Invite> query = em
                .createQuery("SELECT i FROM Invite i WHERE"
                        + " i.result = :result"
                        + " ORDER BY i.dateTime DESC", Invite.class)
                .setParameter("result", result);
        return query.getResultList();
    }

    @Override
    public void save(Invite obj) {
        em.persist(obj);
    }

    @Override
    public void update(Invite obj) {
        em.merge(obj);
    }

    @Override
    public boolean hasPendentOrAcceptedInvite(Long candidateId, Long offerId) {
        
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(i) FROM Invite i"
                + " WHERE i.offer.id = :offerId"
                + " AND i.invited.id = :invitedId AND ( i.result = :none OR i.result = :accepted )", Long.class)
                .setParameter("offerId", offerId)
                .setParameter("invitedId", candidateId)
                .setParameter("none", InviteResult.NONE)
                .setParameter("accepted", InviteResult.ACCEPTED);
        
        Long result = query.getSingleResult();
        return result > 0;
    }
    
}
