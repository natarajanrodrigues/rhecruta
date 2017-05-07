/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.GithubRepository;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.RankedUser;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation.SimpleUser;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EvaluationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author natarajan
 */
@Remote(EvaluationService.class)
@Stateless
public class EvaluationServiceImpl implements EvaluationService{
    
    private static String URL = "http://localhost:8090/suggestions-web/api/";
    private Client client = ClientBuilder.newClient();
    private WebTarget target = client.target(URL);
    
    
    @Override
    public SimpleUser createSimpleUser(Candidate candidate) {
        
        SimpleUser simpleUser = new SimpleUser(candidate);
        Response response = target
                .path("user")
                .request()
                .header("Authorization", "Basic bmV1cm86bWFuY2Vy")
                .post(Entity.entity(simpleUser, MediaType.APPLICATION_JSON));
        
        if (response.getStatusInfo().equals(Status.CREATED)) {
            /*    
            Se o status da reposta foi Created pode converter o resultado
            num SimpleUser; deu tudo certo.
            
            Guarde o id do simpleUser retornado e associe ao candidato para o 
            qual você vai fazer as buscas sobre as informações do linkedin e github.
            */
            SimpleUser resultUser = response.readEntity(SimpleUser.class);
            System.out.println("[Usuário recebido do Suggestions]: " + resultUser);
            return resultUser;
        } else {
            String responseString = response.readEntity(String.class);
            System.out.println("Veja o erro: " + responseString);
            return null;
        }
    }
    
    @Override
    public Set<String> getSkills(Candidate candidate) {
        Set<String> skills = new TreeSet<>();
        List<GithubRepository> repositories = getRepitories(candidate);
        repositories.forEach((repository) -> {
            skills.addAll(repository.getLanguages());
        });
        return skills;
    }
    
    @Override
    public double getMatch(Offer offer, Candidate candidate) {
        List<String> offerSkills = offer.getSkills();
        Set<String> candidateSkills = getSkills(candidate);
        
        int matched = 0;
        for(String offerSkill : offerSkills) {
            if(candidateSkills.stream()
                    .anyMatch((p) -> p.equalsIgnoreCase(offerSkill))){
                matched++;
            }
        }
        
        return (double) matched / (double) offerSkills.size();
    }
    
    @Override
    public List<SimpleUser> searchSimpleUser(Candidate candidate) {
        
        SimpleUser simpleUser = new SimpleUser(candidate);
        Response response = target
                .path("search/user")
                .queryParam("github_account", simpleUser.getGithubAccount())
                .queryParam("linkedin_account", simpleUser.getLinkedinAccount())
                .request()
                .header("Authorization", "Basic bmV1cm86bWFuY2Vy")
                .get();
        
        String stringResult = response.readEntity(String.class);
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            List<SimpleUser> users
                    = objectMapper.readValue(stringResult,
                            new TypeReference<List<SimpleUser>>() {
                    });

            return users;
        } catch (IOException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }
    
    @Override
    public List<SimpleUser> searchSimpleUserWithOr(Candidate candidate) {
        
        SimpleUser simpleUser = new SimpleUser(candidate);
        Response response = target
                .path("search/user/in")
                .queryParam("github_account", simpleUser.getGithubAccount())
                .queryParam("linkedin_account", simpleUser.getLinkedinAccount())
                .request()
                .header("Authorization", "Basic bmV1cm86bWFuY2Vy")
                .get();
        
        String stringResult = response.readEntity(String.class);
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            List<SimpleUser> users
                    = objectMapper.readValue(stringResult,
                            new TypeReference<List<SimpleUser>>() {
                    });

            return users;
        } catch (IOException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }
    
    @Override
    public List<GithubRepository> getRepitories(Candidate candidate) {
        
        Response response = target
                .path("user/{user_id}/project")
                .resolveTemplate("user_id", candidate.getIdEvaluation())
                .request()
                .header("Authorization", "Basic bmV1cm86bWFuY2Vy")
                .get();
        
        String stringResult = response.readEntity(String.class);
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            List<GithubRepository> users
                    = objectMapper.readValue(stringResult,
                            new TypeReference<List<GithubRepository>>() {
                    });

            return users;
        } catch (IOException ex) {
            Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
    
    @Override
    public double getRank(Candidate candidate) {

        if (candidate.getIdEvaluation() != null) {
            Response response = target
                    .path("rank/{user_id}")
                    .resolveTemplate("user_id", candidate.getIdEvaluation())
                    .request()
                    .header("Authorization", "Basic bmV1cm86bWFuY2Vy")
                    .get();

            String stringResult = response.readEntity(String.class);
            try {
                ObjectMapper objectMapper
                        = new ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                        false);
                RankedUser rankedUser
                        = objectMapper.readValue(stringResult,
                                new TypeReference<RankedUser>() {
                        });

                return rankedUser.getRanking();
            } catch (IOException ex) {
                Logger.getLogger(EvaluationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return 0;
    }

}
