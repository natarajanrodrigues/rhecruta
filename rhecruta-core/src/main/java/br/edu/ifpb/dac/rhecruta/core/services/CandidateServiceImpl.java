/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.SimpleUser;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.UniqueFieldException;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EvaluationService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    @EJB
    private EvaluationService evaluationService;
    @EJB
    private UserService userService;
    
    @Override
    public Candidate getByUser(User user) {
        return candidateDAO.getCandidateByUser(user);
    }

    @Override
    public void save(Candidate candidate) {
        try {
            validateCandidate(candidate);
            candidateDAO.save(candidate);
        } catch(UniqueFieldException | IllegalArgumentException e) {
            throw new EJBException(e.getMessage());
        }
        
    }
    
    private void validateCandidate(Candidate candidate) throws UniqueFieldException {
        //procurar por administrator com a mesmo email
        Candidate byEmail = candidateDAO.getByEmail(candidate.getUser().getCredentials().getEmail());
        if (byEmail != null)
            throw new UniqueFieldException("A user with this email already exists");
        //procurar por administrator com mesmo cpf
        Candidate byCPF = candidateDAO.getByCPF(candidate.getCpf());
        if (byCPF != null) 
            throw new IllegalArgumentException("A candidate with this CPF already exists");
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
            
            List<SimpleUser> resultSearch = evaluationService.searchSimpleUser(candidate);
            SimpleUser suggestionUser;
            if (resultSearch.size() == 1) {
                suggestionUser = resultSearch.get(0);
                candidate.setIdEvaluation(suggestionUser.getId());
            } else {
                suggestionUser = evaluationService.createSimpleUser(candidate);
                System.out.println("[Simple user Created]: "+ suggestionUser);
            }
            
            if (suggestionUser == null) {
                throw new EJBException("Invalid github user");
            }
            candidate.setIdEvaluation(suggestionUser.getId());
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
