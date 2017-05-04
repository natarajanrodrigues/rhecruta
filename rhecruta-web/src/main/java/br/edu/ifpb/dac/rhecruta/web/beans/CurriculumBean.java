/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CurriculumService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class CurriculumBean {
    
    
    @Inject
    private CurriculumService curriculumService;
    
    private Part part;
    
    public String uploadFile(Candidate loggedCandidate) {
        
        Long candidateId = loggedCandidate.getId();
        try {
            if(part != null) {
                byte[] bytes = IOUtils.toByteArray(part.getInputStream());
                Curriculum curriculum = new Curriculum(candidateId, 
                        getFileName(part), 
                        bytes);
                curriculumService.upload(curriculum);
                addMessage("curriculumMsg",
                    createMessage("Your curriculum was successfully uploaded!",
                            FacesMessage.SEVERITY_INFO));
            } else {
                addMessage("curriculumMsg",
                    createMessage("Please. Select a pdf file before pressing \"UPLOAD\" button.",
                            FacesMessage.SEVERITY_INFO));
            }
        } catch (IOException ex) {
            
        }
        
        return null;
    }
    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
    
    private FacesMessage createMessage(String text, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }

    private void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
    
    
    
}
