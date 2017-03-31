/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
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

@Stateless
@Local(OfferDAO.class)
public class OfferDAOJpaImpl implements OfferDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager entityManager;

    @Override
    public void save(Offer offer) {
        entityManager.persist(offer);
    }

    @Override
    public void update(Offer offer) {
        entityManager.merge(offer);
    }

    @Override
    public void remove(Offer offer) {
        entityManager.remove(offer);
    }

    @Override
    public List<Offer> listAll() {
        TypedQuery<Offer> query = entityManager
                .createQuery("SELECT o FROM Offer o", Offer.class);
        return query.getResultList();
    }

    @Override
    public Offer get(Long id) {
        return entityManager.find(Offer.class, id);
    }
    
}
