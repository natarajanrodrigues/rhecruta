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
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.EntityNotFoundException;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private EmailRequester emailRequester;

    @Override
    public void save(Enterview enterview) {
        System.out.println("[EnterviewServiceImpl] Enterview to be saved: " + enterview);
        Email createdEmail = createEmail(enterview, true);
        try {
            validate(enterview);
            enterviewDAO.save(enterview);
            emailRequester.send(createdEmail);
        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
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
        offer.getAdministrators().size();

        List<Administrator> appraisers = new ArrayList<>();

        //Filtering appraisers
        //Note: IF JPA WAS COMPATIBLE WITH STREAMS I WOULD LOVE TO USE IT
        for (Administrator admin : offer.getAdministrators()) {
            if (admin.getUser().getRole().equals(Role.APPRAISER)) {
                appraisers.add(admin);
            }
        }

        if (appraisers.isEmpty()) {
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

        //Iterate over the offer appraisers
        for (Administrator appraiser : appraisers) {

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
        Email createdEmail = createEmail(enterview, false);
        if (!enterview.isFinished()) {
            Enterview found = enterviewDAO.findById(enterview.getId());
            enterviewDAO.delete(found);
            emailRequester.send(createdEmail);
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

    private Email createEmail(Enterview enterview, boolean marked) {
        String message;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");;

        if (marked) {
            StringBuilder sb = new StringBuilder();
            sb.append("Olá ").append(enterview.getCandidate().getFirstname()).append(" estamos felizes em confirmar "
                    + "que foi marcada uma entrevista para você. Veja os detalhes da oferta e da entrevista:\n")
                    .append(prettyOfferToString(enterview.getOffer()))
                    .append("Data/Hora Início: ").append(dtf.format(enterview.getStart())).append("\n")
                    .append("Data/Hora Final: ").append(dtf.format(enterview.getEnd()));

            message = sb.toString();
        } else {

            StringBuilder sb = new StringBuilder();
            sb.append("Olá ").append(enterview.getCandidate().getFirstname()).append(", infelizmente a entrevista da oferta "
                    + "descrita abaixo foi cancelada. Aguarde por novas instruções.:\n")
                    .append(prettyOfferToString(enterview.getOffer()));

            message = sb.toString();
        }
        Email email = new Email();
        email.setFrom("rhecrutapp@gmail.com");
        email.setTo(enterview.getCandidate().getUser().getCredentials().getEmail());
        email.setRequestedDate(LocalDateTime.now());
        String subject = marked ? "Entrevista marcada" : "Entrevista cancelada";
        email.setSubject(subject);
        email.setText(message);
        return email;
    }

    private static String prettyOfferToString(Offer offer) {
        StringBuilder sb = new StringBuilder();
        String recuo = "\n";
        sb
                .append("Oferta: \n")
                .append("Descrição: ").append(offer.getDescription()).append(recuo)
                .append("Vagas: ").append(offer.getVacancies()).append(recuo)
                .append("Habilidades solicitadas: ").append(offer.getSkills().toString()).append(recuo);
        return sb.toString();
    }

    @Override
    public Enterview getByOfferAnCandidate(Offer offer, Candidate candidate) {
        Enterview found = enterviewDAO
                .getByOfferAndCandidate(offer.getId(), candidate.getId());
        if(found == null)
            throw new EJBException(new EntityNotFoundException("There's no Intervew scheduled to "
                    + offer.getDescription()+ " and "+candidate.getFirstname()));
        return found;
    }

}
