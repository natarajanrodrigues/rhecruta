/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.SystemEvaluationDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.SystemEvaluation;
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

@Local(SystemEvaluationDAO.class)
@Stateless
public class SystemEvaluationDAOJpaImpl implements SystemEvaluationDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager manager;

    @Override
    public void save(SystemEvaluation systemEvaluation) {
        this.manager.persist(systemEvaluation);
    }

    @Override
    public List<SystemEvaluation> listByOffer(Offer offer) {
        TypedQuery<SystemEvaluation> query = manager
                .createQuery("SELECT se FROM SystemEvaluation se"
                + " WHERE se.offer.id = :offerId", SystemEvaluation.class)
                .setParameter("offerId", offer.getId());
        return query.getResultList();
    }

    @Override
    public SystemEvaluation getByOfferAndCandidate(Offer offer, Candidate candidate) {
        TypedQuery<SystemEvaluation> query = manager
                .createQuery("SELECT se FROM SystemEvaluation se"
                + " WHERE se.offer.id = :offerId"
                + " AND se.candidate.id = :candidateId", SystemEvaluation.class)
                .setParameter("offerId", offer.getId())
                .setParameter("candidateId", candidate.getId());
        List<SystemEvaluation> result = query.getResultList();
        if(result.isEmpty())
            return null;
        return result.get(0);
    }
    
}
