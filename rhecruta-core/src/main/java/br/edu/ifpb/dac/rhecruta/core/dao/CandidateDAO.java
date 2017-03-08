/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao;

import br.edu.ifpb.dac.rhecruta.shared.domain.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.User;

/**
 *
 * @author Pedro Arthur
 */
public interface CandidateDAO extends DAO<Candidate> {
    
    Candidate getCandidateByUser(User user);
    void delete(Candidate candidate);
}
