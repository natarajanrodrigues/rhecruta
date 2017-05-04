/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;

/**
 * 
 * @author Pedro Arthur
 */
public interface CurriculumDAO {
    
    Curriculum getByCandidateId(Long candidateId);
    void upload(Curriculum curriculum);
}
