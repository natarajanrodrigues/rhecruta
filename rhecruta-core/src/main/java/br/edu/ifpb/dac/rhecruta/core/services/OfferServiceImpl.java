/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.EnterviewDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.SystemEvaluationDAO;
import br.edu.ifpb.dac.rhecruta.core.mdb.StartMDB;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.EntityNotFoundException;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
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

@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless
@Remote(OfferService.class)
public class OfferServiceImpl implements OfferService {
    
    @EJB
    private OfferDAO offerDAO;
    @EJB
    private EnterviewDAO enterviewDAO;
    @EJB
    private EmailRequester emailRequester;
    @EJB
    private SystemEvaluationDAO systemEvaluationDAO;
    
    @Inject
    @JMSConnectionFactory(value = "jms/dac/dacConnectionFactory")
    private JMSContext jmsContext;

    @Resource(lookup = "jms/dac/rhecruta/newOfferQueue")
    private Queue newOfferQueue;
    
    @EJB
    private SystemEvaluationSenderMessage systemEvaluationSender;
    
    
    @EJB
    private StartMDB sb;

    @PostConstruct
    public void init() {
        System.out.println("[INIT MDB COMEÇOU: NewOfferListener]");
        if (!sb.isInit()) {
            System.out.println("NUNCA DEVE ACONTECER");
        }
        System.out.println("[INICIOU MDB TERMINOU: NewOfferListener]");
    }
    
    
    @Override
    public void save(Offer offer) {
        offer.setCreationDateTime(LocalDateTime.now());
        Long id = offerDAO.save(offer);
        System.out.println("[OfferServiceImpl] Offer was successfully saved. ID: "+id);
        if(offer.getType().equals(OfferType.OPEN)) {
            sendToNewOfferQueue(id);
        }
    }
    
    private void sendToNewOfferQueue(Long id) {
        Message message = createMessage(id);
        JMSProducer producer = jmsContext.createProducer();
        producer.send(newOfferQueue, message);
    }
    
    private Message createMessage(Long offerId) {
        Message message = jmsContext.createTextMessage(offerId.toString());
        return message;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void remove(Offer offer) {
        try {
            removingValidate(offer);
            offer.unsubscribeAll();
            offerDAO.update(offer);
            systemEvaluationDAO.deleteByOffer(offer);
            Offer found = find(offer.getId());
            offerDAO.remove(found);
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }
    
    private void removingValidate(Offer offer) {
        if(countScheduledInterview(offer) > 0) 
            throw new IllegalArgumentException("You can't remove a offer which"
                    + " already has scheduled interviews!");
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
    public Long countScheduledInterview(Offer offer) {
        return enterviewDAO.countInterviewsByOffer(offer);
    }

    @Override
    public void update(Offer offer) {
        offerDAO.update(offer);
    }
    
    @Override
    public void subscribe(Candidate candidate, Offer offer) {
        offer.subscribe(candidate);
        offerDAO.update(offer);
        systemEvaluationSender.sendToSystemEvaluationQueue(offer, candidate);
    }
    
    @Override
    public void unsubscribe(Candidate candidate, Offer offer) {
        offer.unsubscribe(candidate);
        offerDAO.update(offer);
    }

    @Override
    public List<Offer> listAll() {
        return offerDAO.listAll();
    }
    
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
