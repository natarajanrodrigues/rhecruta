/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CurriculumDAO;
import br.edu.ifpb.dac.rhecruta.core.services.upload.DropboxBean;
import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.SearchErrorException;
import com.dropbox.core.v2.files.SearchMatch;
import com.dropbox.core.v2.files.SearchResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Pedro Arthur
 */

@Local(CurriculumDAO.class)
@Stateless
public class CurriculumDAODbxImpl implements CurriculumDAO {
    
    private final String curriculumsPath = "/curriculums";
    
    @EJB
    private DropboxBean dropbox;
    private DbxUserFilesRequests files;
    
    @PostConstruct
    private void init() {
        this.files = this.dropbox.getClient().files();
    }
    
    //get a curriculum by candidateId
    @Override
    public Curriculum getByCandidateId(Long candidateId) {
        
        System.out.println("[CurriculumDAODbxImpl] Searching by candidateId: "+candidateId);
        try {
            String candidateFolder = getCandidateFolderPath(candidateId);
            System.out.println("[CurriculumDAODbxImpl] candidateFolderPath: "+candidateFolder);
            SearchResult searchResult = files
                    .search(candidateFolder, candidateId.toString());
            
            List<SearchMatch> matches = searchResult.getMatches();
            if(matches.isEmpty()) {
                System.out.println("[CurriculumDAODbxImpl] No matches.");
                return null;
            } else {
                SearchMatch found = matches.get(0);
                String fileName = found.getMetadata().getName();
                System.out.println("[CurriculumDAODbxImpl] Match: "+fileName);
                String fullName = fullFileName(candidateFolder, fileName);
                System.out.println("[CurriculumDAODbxImpl] fullName: "+fullName);
                byte[] bytes = IOUtils.toByteArray(files.download(fullName)
                        .getInputStream());
                System.out.println("[CurriculumDAODbxImpl] "+fullName+" was successfully downloaded!");
                return new Curriculum(candidateId, fileName, bytes);
            }
        }
        catch (SearchErrorException ex) {
            return null;
        }
        catch (DbxException | IOException ex) {
            System.out.println("[CurriculumDAODbxImpl] Exception. :/");
            Logger.getLogger(CurriculumDAODbxImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("[CurriculumDAODbxImpl] Returning null...");
        return null;
    }
    
    private String extension(String filename) {
        return FilenameUtils.getExtension(filename);
    }
    
    private String fullFileName(String candidateFolder, String filename) {
        StringBuilder builder = new StringBuilder(candidateFolder);
        builder.append('/').append(filename);
        return builder.toString();
    }
    
    //get candidate folder path, that means /curriculums/1/1.pdf
    private String getCandidateFolderPath(Long candidateId) {
        StringBuilder builder = new StringBuilder(curriculumsPath);
        builder.append('/').append(candidateId.toString());
        return builder.toString();
    }
    
    //upload a file. if exists a already named path, delete it and then upload the
    //new one.
    @Override
    public void upload(Curriculum curriculum) {
        Long id = curriculum.getCandidateId();
        System.out.println("[CurriculumDAODbxImpl] Uploading, candidateId: "+id);
        String path = fullFileName(getCandidateFolderPath(curriculum
                .getCandidateId()), curriculum.getCandidateId().toString() + '.' + extension(
                curriculum.getFilename()));
        System.out.println("[CurriculumDAODbxImpl] fullName: "+path);
        try {
            String existingFileName = exists(curriculum.getCandidateId());
            if(!existingFileName.isEmpty()) {
                System.out.println("[CurriculumDAODbxImpl] Deleting...");
                String target = fullFileName(getCandidateFolderPath(id), existingFileName);
                System.out.println("[CurriculumDAODbxImpl] target: "+target);
                delete(target);
                System.out.println("[CurriculumDAODbxImpl] \""+path+"\" was successfully deleted!");
            }
            System.out.println("[CurriculumDAODbxImpl] uploading... ");
            ByteArrayInputStream bais = new ByteArrayInputStream(curriculum.getBytes());
            files.upload(path).uploadAndFinish(bais);
            System.out.println("[CurriculumDAODbxImpl] "+path+" was succesfully uploaded!");
        } catch (DbxException | IOException ex) {
            Logger.getLogger(CurriculumDAODbxImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //delete based on a path
    private void delete(String path) {
        try {
            Metadata delete = files.delete(path);
        } catch (DbxException ex) {
            Logger.getLogger(CurriculumDAODbxImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //verify if a curriculum exists
    private String exists(Long candidateId) throws DbxException {
        try {
            String candidateFolder = getCandidateFolderPath(candidateId);
            System.out.println("[CurriculumDAODbxImpl] candidateFolderPath: "+candidateFolder);
            SearchResult searchResult = files
                    .search(candidateFolder, candidateId.toString());
            
            List<SearchMatch> matches = searchResult.getMatches();
            if(matches.isEmpty()) {
                System.out.println("[CurriculumDAODbxImpl] no matches.");
                return "";
            }
            else {
                System.out.println("[CurriculumDAODbxImpl] Exists!.");
                SearchMatch match = matches.get(0);
                return match.getMetadata().getName();
            }
        }
        catch (SearchErrorException ex) {
            System.out.println("[CurriculumDAODbxImpl] no matches.");
            return "";
        } catch (DbxException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public boolean hasCurriculum(Long candidateId) {
        System.out.println("[CurriculumDAODbxImpl] Searching by candidateId: "+candidateId);
        try {
            String candidateFolder = getCandidateFolderPath(candidateId);
            System.out.println("[CurriculumDAODbxImpl] candidateFolderPath: "+candidateFolder);
            SearchResult searchResult = files.search(candidateFolder, candidateId.toString());
            List<SearchMatch> matches = searchResult.getMatches();
            
            if(matches.isEmpty()) {
                System.out.println("[CurriculumDAODbxImpl] No matches.");
              return false;
            } 
            
        } catch (DbxException ex) {
            System.out.println("[CurriculumDAODbxImpl] Exception. :/");
            Logger.getLogger(CurriculumDAODbxImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
        System.out.println("[CurriculumDAODbxImpl] Returning false...");
        return false;
    }
    
    
}
