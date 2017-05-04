/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.EntityNotFoundException;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.persistence.NoResultException;

/**
 *
 * @author Pedro Arthur
 */

@Stateless
@Remote(OfferService.class)
public class OfferServiceImpl implements OfferService {
    
    @EJB
    private OfferDAO offerDAO;
    @EJB
    private EmailRequester emailRequester;
    
    @Inject
    @JMSConnectionFactory(value = "jms/dac/dacConnectionFactory")
    private JMSContext jmsContext;

    @Resource(lookup = "jms/dac/rhecruta/newOfferQueue")
    private Queue newOfferQueue;

    
    @Override
    public void save(Offer offer) {
        offer.setCreationDateTime(LocalDateTime.now());
        offerDAO.save(offer);
        System.out.println("Offer: "+offer);
//        sendToNewOfferQueue(offer);
    }
    
    private void sendToNewOfferQueue(Offer offer) {
        Message message = createMessage(offer.getId());
        JMSProducer producer = jmsContext.createProducer();
        producer.send(newOfferQueue, message);
    }
    
    private Message createMessage(Long offerId) {
        Message message = jmsContext.createTextMessage(offerId.toString());
        return message;
    }

    @Override
    public void remove(Offer offer) {
        try {
            Offer found = find(offer.getId());
            offerDAO.remove(found);
        } catch (EntityNotFoundException ex) {
            throw new EJBException(ex);
        }
    }
    
    private Offer find(Long id) throws EntityNotFoundException {
        try {
            Offer found = offerDAO.get(id);
            return found;
        } catch (NoResultException ex) {
            throw new EntityNotFoundException("You're trying to remove a non existent offer!");
        }
    }

    @Override
    public void update(Offer offer) {
        offerDAO.update(offer);
    }

    @Override
    public List<Offer> listAll() {
        return offerDAO.listAll();
    }
//    
//    @Override
//    public List<Offer> getByAppraiser(Administrator appraiser) {
//        return offerDAO.getByAdministrator(appraiser);
//    }
    
    @Override
    public Offer getById(Long offerId) {
        return offerDAO.getById(offerId);
    }

    @Override
    public List<Offer> getByType(OfferType offerType) {
        return offerDAO.getByType(offerType);
    }

    @Override
    public boolean isSubscribed(Long offerId, Candidate candidate) {
        return offerDAO.isSubscribed(offerId, candidate);
    }

    @Override
    public List<Offer> getByCandidate(Candidate candidate) {
        return offerDAO.getByCandidate(candidate);
    }

//    @Override
//    public boolean isAttached(Long offerId, Long administratorId) {
//        System.out.println(offerId + " " + administratorId);
//        return offerDAO.isAttached(offerId, administratorId);
//    }

    @Override
    public List<Candidate> getSubscribers(Offer offer) {
        return offerDAO.getSubscribers(offer);
    }

    @Override
    public List<Offer> getByManager(Administrator manager) {
        return offerDAO.getByManager(manager);
    }

    @Override
    public List<Offer> getByAppraiser(Administrator appraiser) {
        return offerDAO.getByAppraiser(appraiser);
    }

    @Override
    public List<Offer> getMonthOffers() {
        return offerDAO.getMonthOffers();
    }
    
    @Override
    public Object[] getMonthOffersByLanguage() {
        return offerDAO.getMonthOffersByLanguage();
    }
    
    @Override
    public Object[] getMonthCandidatesPerVacancyBySkill() {
        return offerDAO.getMonthCandidatesPerVacancyBySkill();
    }
    
}
