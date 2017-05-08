/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.EnterviewDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.OfferDAO;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.EntityNotFoundException;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewHandlerService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */
@Remote(EnterviewService.class)
@Stateless
public class EnterviewServiceImpl implements EnterviewService {

    @EJB
    private EnterviewDAO enterviewDAO;
    @EJB
    private OfferDAO offerDAO;
    
    @EJB
    private EnterviewHandlerService enterviewHandler;

    @Override
    public void save(Enterview enterview) {
        System.out.println("[EnterviewServiceImpl] Enterview to be saved: " + enterview);
        try {
            validate(enterview);
            Long savedInterview = enterviewDAO.save(enterview);
            enterview.setId(savedInterview);
            enterviewHandler.handleCriation(enterview);

        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }
    
    @Override
    public void update(Enterview enterview) {
        System.out.println("[EnterviewServiceImpl] Updating to be saved: " + enterview);
        enterviewDAO.update(enterview);
    }

    private void validate(Enterview enterview) {
        //obvious validation
        dummyValidate(enterview);
        //domain validation
        domainValidate(enterview);
    }

    private void dummyValidate(Enterview enterview) {
        if (enterview == null) {
            throw new IllegalArgumentException("You're passing an null enterview");
        }
        if (enterview.getOffer() == null) {
            throw new IllegalArgumentException("This enterview doesn't have an offer!");
        }
        if (enterview.getCandidate() == null) {
            throw new IllegalArgumentException("Who r u gonna enterview? a null existent candidate?");
        }
    }

    private void domainValidate(Enterview enterview) {
        //domain validation
        Offer offer = offerDAO.get(enterview.getOffer().getId());

        Administrator appraiser = offer.getAppraiser();

        if (appraiser == null) {
            throw new IllegalArgumentException("There's no appraisers in the enterview."
                    + " Who is gonna guide the enterview?");
        }

        LocalDateTime enterviewStart = enterview.getStart();
        LocalDateTime enterviewEnd = enterview.getEnd();

        if (enterviewStart.isAfter(enterviewEnd)) {
            throw new IllegalArgumentException("Wrong scheduling. Starts is after the end.");
        }

        if (enterviewStart.isBefore(LocalDateTime.now().plus(30, ChronoUnit.MINUTES))) {
            throw new IllegalArgumentException("Scheduller too short. The interview should begin at least 30 minutes.");
        }

        //Get the appraiser enterviews scheduled
        List<Enterview> enterviews = enterviewDAO
                .listByAppraiser(appraiser);

        //iterate over the appraiser enterviews
        for (Enterview appraiserEnterview : enterviews) {

            //If the enterview period get in chock with any other appraiser enterview
            //throw an exception
            if ((appraiserEnterview.getStart().isAfter(enterviewEnd)
                    && appraiserEnterview.getEnd().isAfter(enterviewEnd))
                    || (appraiserEnterview.getStart().isBefore(enterviewStart)
                    && appraiserEnterview.getEnd().isBefore(enterviewStart))) {

            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                throw new IllegalArgumentException("The appraiser "
                        + appraiser.getFirstname() + ' ' + appraiser.getLastname()
                        + " will be busy at this time. He has an enterview schedule "
                        + "to " + appraiserEnterview.getStart().format(dtf) + " - " + appraiserEnterview.getEnd().format(dtf) + "."
                        + " Please choose another period or choose another"
                        + " appraiser to guide this enterview.");
            }
        }

        //I think this shit is able go Haha'
    }

    @Override
    public List<Enterview> listAll() {
        return enterviewDAO.listAll();
    }

    @Override
    public void evaluate(Enterview enterview, Double score) {
        try {
            validateEvaluation(enterview, score);
            enterview.setScore(score);
            enterviewDAO.update(enterview);
        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void cancel(Enterview enterview) {
        
        if (!enterview.isFinished()) {
            Enterview found = enterviewDAO.findById(enterview.getId());
            enterviewHandler.handleDeletion(enterview);
            enterviewDAO.delete(found);
            
        } else {
            throw new EJBException(
                    new IllegalArgumentException("You can't cancel a finished interview!")
            );
        }
    }

    private void validateEvaluation(Enterview enterview, Double score) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (enterview == null) {
            throw new IllegalArgumentException("You're trying to evaluate a null enterview.");
        }
        if (score == null || score < 0) {
            throw new IllegalArgumentException("You're can't evaluate an enterview the score: " + score);
        }
        if (!enterview.isFinished()) {
            throw new IllegalArgumentException("You can't evaluate this enterview yet."
                    + " Please wait 'til " + enterview.getEnd().format(dtf));
        }
        if (enterview.isFinished() && enterview.getScore() >= 0) {
            throw new IllegalArgumentException("This enterview was already evaluated!");
        }
    }


    @Override
    public List<Enterview> listByOffer(Offer offer) {
        return enterviewDAO.listByOffer(offer);
    }

    @Override
    public Enterview getByOfferAnCandidate(Offer offer, Candidate candidate) {
        Enterview found = enterviewDAO
                .getByOfferAndCandidate(offer.getId(), candidate.getId());
        if (found == null) {
            throw new EJBException(new EntityNotFoundException("There's no Intervew scheduled to "
                    + offer.getDescription() + " and " + candidate.getFirstname()));
        }
        return found;
    }

    @Override
    public List<Enterview> listByCandidate(Candidate candidate) {
        return enterviewDAO.listByCandidate(candidate);
    }

    @Override
    public List<Enterview> listByAppraiser(Administrator administrator) {
        return enterviewDAO.listByAppraiser(administrator);
    }

    @Override
    public List<Enterview> listByManager(Administrator administrator) {
        return enterviewDAO.listByManager(administrator);
    }

}
