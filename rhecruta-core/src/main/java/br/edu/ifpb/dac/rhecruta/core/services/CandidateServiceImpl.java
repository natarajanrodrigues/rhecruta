/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.UserDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
import javax.ejb.EJB;
import javax.ejb.Remote;

/**
 *
 * @author Pedro Arthur
 */

@Remote(CandidateService.class)
public class CandidateServiceImpl implements CandidateService {
    
    @EJB
    private CandidateDAO candidateDAO;
    @EJB
    private UserDAO userDAO;

    @Override
    public Candidate getByUser(User user) {
        return candidateDAO.getCandidateByUser(user);
    }

    @Override
    public void save(Candidate candidate) {
        userDAO.save(candidate.getUser());
        candidateDAO.save(candidate);
    }

    @Override
    public void update(Candidate candidate) {
        userDAO.update(candidate.getUser());
        candidateDAO.update(candidate);
    }

    @Override
    public void delete(Candidate candidate) {
        candidateDAO.delete(candidate);
    }
    
}
