/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.upload;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CurriculumDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.UploadErrorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Pedro Arthur
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue"
    )
    ,
    @ActivationConfigProperty(
            propertyName = "destinationLookup",
            propertyValue = "jms/dac/filesToUploadQueue"
    )
})
public class RequestUploadListener implements MessageListener {

    @EJB
    private DropboxBean dropboxBean;
    @EJB
    private CurriculumDAO curriculumDAO;

    @Override
    public void onMessage(Message message) {
        
        try {
            Curriculum file = message.getBody(Curriculum.class);
            upload(file);
        } catch (JMSException ex) {
            Logger.getLogger(RequestUploadListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void upload(Curriculum file) {
        curriculumDAO.upload(file);
    }


}
