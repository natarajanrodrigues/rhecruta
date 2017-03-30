/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
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

    @Override
    public Candidate getByUser(User user) {
        return candidateDAO.getCandidateByUser(user);
    }
    
    public void denyCandidate(Candidate candidate) {
        //deleta candidato
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
    public void denyRequest(Candidate candidate) {
        //Notificate Candidate
        delete(candidate);
    }
    
}
