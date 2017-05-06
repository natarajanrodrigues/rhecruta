/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.GithubRepository;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.SimpleUser;
import java.util.List;

/**
 *
 * @author Natarajan
 */
public interface EvaluationService {
    
    SimpleUser createSimpleUser(Candidate candidate);
    List<SimpleUser> searchSimpleUser(Candidate candidate);
    List<GithubRepository> getRepitories(Candidate candidate);
    double getRank(Candidate candidate);
    List<SimpleUser> searchSimpleUserWithOr(Candidate candidate);
}
