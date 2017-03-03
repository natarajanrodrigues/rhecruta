/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain;

import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferStatus;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class Offer {
    
    private Long id;
    private OfferType type;
    private final List<String> skills;
    private String description;
    private Integer vacancies;
    private OfferStatus status;
    private final List<CandidateUser> candidates;

    public Offer(Long id, OfferType type, List<String> skills, String description, Integer vacancies, OfferStatus status, List<CandidateUser> candidates) {
        this.id = id;
        this.type = type;
        this.skills = skills;
        this.description = description;
        this.vacancies = vacancies;
        this.status = status;
        this.candidates = candidates;
    }
    
    public Offer() {   
        this.skills = new ArrayList<>();
        this.candidates = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfferType getType() {
        return type;
    }

    public void setType(OfferType type) {
        this.type = type;
    }

    public List<String> getSkills() {
        return Collections.unmodifiableList(this.skills);
    }

    public void addSkill(String skill) {
        this.skills.add(skill);
    }
    
    public void addSkills(List<String> skills) {
        this.skills.addAll(skills);
    }
    
    public void removeSkill(String skill) {
        this.skills.remove(skill);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVacancies() {
        return vacancies;
    }

    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public List<CandidateUser> getCandidates() {
        return Collections.unmodifiableList(candidates);
    }
    
    public void subscribe(CandidateUser candidate) {
        this.candidates.add(candidate);
    }
    
    public void unsubscribe(CandidateUser candidate) {
        this.candidates.remove(candidate);
    }

    @Override
    public String toString() {
        return "Offer{" + "id=" + id + ", type=" + type + ", skills=" + skills + ", description=" + description + ", vacancies=" + vacancies + ", status=" + status + '}';
    } 
}
