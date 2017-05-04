/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.EnterviewDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
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
@Local(EnterviewDAO.class)
@Stateless
public class EnterviewDAOJpaImpl implements EnterviewDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager manager;

    @Override
    public List<Enterview> listAll() {
        TypedQuery<Enterview> query = manager
                .createQuery("SELECT e FROM Enterview e", Enterview.class);
        return query.getResultList();
    }

    @Override
    public Enterview findById(Long id) {
        return manager.find(Enterview.class, id);
    }

    @Override
    public void save(Enterview obj) {
        manager.persist(obj);
    }

    @Override
    public void update(Enterview obj) {
        manager.merge(obj);
    }

    //TALVEZ TENHA QUE ALTERAR ISSO AQUI
    @Override
    public List<Enterview> listByAppraiser(Administrator appraiser) {
        TypedQuery<Enterview> query = manager.createQuery("SELECT e FROM Enterview e"
                + " WHERE e.offer.appraiser = :appraiser", 
                Enterview.class)
                .setParameter("appraiser", appraiser);
        return query.getResultList();
    }

    @Override
    public void delete(Enterview enterview) {
        this.manager.remove(enterview);
    }

    @Override
    public List<Enterview> listByOffer(Offer offer) {
        TypedQuery<Enterview> query = manager.createQuery("SELECT e FROM Enterview e"
                + " WHERE e.offer = offer", Enterview.class)
                .setParameter("offer", offer);
        return query.getResultList();

    }

    @Override
    public Enterview getByOfferAndCandidate(Long offerId, Long candidateId) {
        TypedQuery<Enterview> query = manager
                .createQuery("SELECT e"
                        + " FROM Enterview e"
                        + " WHERE e.offer.id = :offerId"
                        + " AND e.candidate.id = :candidateId", Enterview.class)
                .setParameter("offerId", offerId)
                .setParameter("candidateId", candidateId);

        List<Enterview> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }

    }

    @Override
    public List<Enterview> listByCandidate(Candidate candidate) {
        TypedQuery<Enterview> query = manager.createQuery("SELECT e FROM Enterview e"
                + " WHERE e.candidate = candidate", Enterview.class)
                .setParameter("candidate", candidate);
        return query.getResultList();
    }

}
