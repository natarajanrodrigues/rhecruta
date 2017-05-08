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
import org.apache.commons.io.FilenameUtils;

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
        try {
            validate(curriculum);
            uploader.upload(curriculum);
        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }
    
    // Criar classe validadora de Curriculum
    private void validate(Curriculum curriculum) {
        if(curriculum == null)
            throw new IllegalArgumentException("You're passing a null curriculum."
                    + " Please, attach your best curriculum and try again!");
        byte[] bytes = curriculum.getBytes();
        String fileName = curriculum.getFilename();
        Long candidateId = curriculum.getCandidateId();
        
        if(bytes == null || bytes.length == 0)
            throw new IllegalArgumentException("You didn't attach any file. Please, "
                    + "attach your best curriculum and try again!");
        if(fileName == null || !getExtension(fileName).equals("pdf"))
            throw new IllegalArgumentException("We only accept pdf files.");
        if(candidateId == null || candidateId <= 0)
            throw new IllegalArgumentException("This file is not related to a candidate."
                    + " Are you logged in?");    
    }
    
    private String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }
    
}
