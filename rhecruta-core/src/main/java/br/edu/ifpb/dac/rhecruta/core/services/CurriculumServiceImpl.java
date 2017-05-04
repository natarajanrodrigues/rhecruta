/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CurriculumDAO;
import br.edu.ifpb.dac.rhecruta.core.services.upload.RequestUploadBean;
import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CurriculumService;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@Remote(CurriculumService.class)
@Stateless
public class CurriculumServiceImpl implements CurriculumService {
    
    @EJB
    private RequestUploadBean uploader;
    
    @EJB
    private CurriculumDAO curriculumDAO;

    @Override
    public Curriculum get(Long candidateId) {
        Curriculum curriculum = curriculumDAO
                .getByCandidateId(candidateId);
        if(curriculum == null) {
            throw new EJBException(new IllegalArgumentException("There is no curriculum registered yet."));
        }
        return curriculum;
    }
    
    @Override
    public void upload(Curriculum curriculum) {
        uploader.upload(curriculum);
    }
    
}
