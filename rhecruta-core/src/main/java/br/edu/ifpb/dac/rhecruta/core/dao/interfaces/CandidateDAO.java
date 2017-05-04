/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface CandidateDAO extends DAO<Candidate> {
    
    Candidate getCandidateByUser(User user);
    void delete(Candidate candidate);
    Candidate get(Long id);
    List<Candidate> listCandidatesToApprove();
    List<Candidate> listApprovedCandidates();
}
