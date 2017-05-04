/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */
@Stateless
@Remote(CandidateService.class)
public class CandidateServiceImpl implements CandidateService {
    
    @EJB
    private CandidateDAO candidateDAO;
    @EJB
    private EmailRequester emailRequester;

    @Override
    public Candidate getByUser(User user) {
        return candidateDAO.getCandidateByUser(user);
    }

    @Override
    public void save(Candidate candidate) {
        candidateDAO.save(candidate);
    }

    @Override
    public void update(Candidate candidate) {
        candidateDAO.update(candidate);
    }

    @Override
    public void delete(Candidate candidate) {
        Candidate found = candidateDAO.get(candidate.getId());
        candidateDAO.delete(found);
    }

    @Override
    public List<Candidate> listCandidatesToApprove() {
        return candidateDAO.listCandidatesToApprove();
    }

    @Override
    public void respondRequest(Candidate candidate, boolean approved) {
        Email createdEmail = createEmail(candidate, approved);
        if(!approved)
            delete(candidate);
        else {
            candidate.getUser().setApproved(true);
            update(candidate);
        }
        emailRequester.send(createdEmail);
    }
    
    private Email createEmail(Candidate candidate, boolean approved) {
        String message;
        if(approved) {
            message = "Olá "+candidate.getFirstname()+", estamos felizes em afirmar"
                + " que sua solicitação de cadastro no site Rhecruta foi aprovada.";
        } else {
            message = "Olá "+candidate.getFirstname()+", sentimos informar"
                + " que sua solicitação de cadastro no site Rhecruta foi negada.";
        }
        Email email = new Email();
        email.setFrom("rhecrutapp@gmail.com");
        email.setTo(candidate.getUser().getCredentials().getEmail());
        email.setRequestedDate(LocalDateTime.now());
        email.setSubject("Sobre sua solicitação de cadastro - Rhecruta");
        email.setText(message);
        return email;
    }

    @Override
    public List<Candidate> listApprovedCandidates() {
        return candidateDAO.listApprovedCandidates();
    }
    
}
