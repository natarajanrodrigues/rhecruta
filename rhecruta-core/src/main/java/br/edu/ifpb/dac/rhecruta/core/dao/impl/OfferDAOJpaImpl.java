/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import java.util.Collections;
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
    public Offer get(Long id) {
        return entityManager.find(Offer.class, id);
    }
    
    @Override
    public List<Offer> listAll() {
        TypedQuery<Offer> query = entityManager
                .createQuery("SELECT o FROM Offer o", Offer.class);
        return query.getResultList();
    }

    @Override
    public List<Offer> getByAdministrator(Administrator admnistrator) {
        TypedQuery<Offer> query = entityManager
                .createQuery("SELECT o FROM Offer o, IN (o.administrators) admin WHERE admin.id = :idAdmin ", Offer.class)
                .setParameter("idAdmin", admnistrator.getId());
        return query.getResultList();
    }

    @Override
    public Offer getById(Long offerId) {
        TypedQuery<Offer> query = entityManager
                .createQuery("SELECT o FROM Offer o WHERE o.id = :idOffer ", Offer.class)
                .setParameter("idOffer", offerId);
        Offer offer = query.getSingleResult();
        offer.getCandidates().size();
        offer.getAdministrators().size();
        offer.getSkills().size();
        return offer;
    }
    
    @Override
    public List<Offer> getByType(OfferType offerType) {
        try {
            TypedQuery<Offer> query = entityManager
                    .createQuery("SELECT o FROM Offer o WHERE o.typeId = :typeId", Offer.class)
                    .setParameter("typeId", offerType.getId());

            List<Offer> resultList = query.getResultList();
            for (Offer o : resultList) {
                o.getAdministrators().size();
                o.getCandidates().size();
                o.getSkills().size();
            }
            return resultList;
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }

    }

    @Override
    public boolean isSubscribed(Long offerId, Candidate candidate) {
        
        TypedQuery<Offer> query = entityManager
                .createQuery("SELECT o FROM Offer o, IN (o.candidates) candidate WHERE o.id = :offerId AND candidate.id = :candidadeId ", Offer.class)
                .setParameter("candidadeId", candidate.getId())
                .setParameter("offerId", offerId);
        List<Offer> resultList = query.getResultList();
        System.out.println("SIZEEEEEE: " + resultList.size());
        return resultList.size() > 0;
    }

    @Override
    public List<Offer> getByCandidate(Candidate candidate) {
        try {
            TypedQuery<Offer> query = entityManager
                    .createQuery("SELECT o FROM Offer o, IN (o.candidates) candidate WHERE candidate.id = :idCandidate ", Offer.class)
                    .setParameter("idCandidate", candidate.getId());
            List<Offer> resultList = query.getResultList();
            for (Offer o : resultList) {
                o.getAdministrators().size();
                o.getCandidates().size();
                o.getSkills().size();
            }
            return query.getResultList();

        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public boolean isAttached(Long offerId, Long administratorId) {

        System.out.println("OFFER: " + offerId);
        System.out.println("ADM: " + administratorId);
        TypedQuery<Offer> query = entityManager
                .createQuery("SELECT o FROM Offer o, IN (o.administrators) admin WHERE admin.id = :idAdmin AND o.id = :idOffer", Offer.class)
                .setParameter("idAdmin", administratorId)
                .setParameter("idOffer", offerId);

        List<Offer> resultList = query.getResultList();
        System.out.println("SIZE: " + resultList.size());

        return resultList.size() > 0;

    }
    
    @Override
    public List<Candidate> getSubscribers(Offer offer) {
        try {
            TypedQuery<Candidate> query = entityManager
                    .createQuery("SELECT candidate FROM Offer o, IN (o.candidates) candidate WHERE o.id = :idOffer", Candidate.class)
                    .setParameter("idOffer", offer.getId());
            List<Candidate> resultList = query.getResultList();
            
            return resultList;

        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }
    
    
    
}
