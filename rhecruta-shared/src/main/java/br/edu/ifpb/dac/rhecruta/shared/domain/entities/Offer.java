/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import br.edu.ifpb.dac.rhecruta.shared.domain.enums.SystemStatus;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Pedro Arthur
 */
@Entity
public class Offer implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "offer_type_id")
    private int typeId;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private final List<String> skills;
    
    private String description;
    private Integer vacancies;
    
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
    
    @Column(name = "status_id")
    private int statusId;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offer_candidates",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id"))
    private List<Candidate> candidates;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Administrator manager;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appraiser_id")
    private Administrator appraiser;

    public Offer(Long id, int typeId, List<String> skills, String description, Integer vacancies, int statusId, List<Candidate> candidates, Administrator manager, Administrator appraiser) {
        this.id = id;
        this.typeId = typeId;
        this.skills = skills;
        this.description = description;
        this.vacancies = vacancies;
        this.statusId = statusId;
        this.candidates = candidates;
        this.manager = manager;
        this.appraiser = appraiser;
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
        return OfferType.parse(this.typeId);
    }

    public void setType(OfferType type) {
        this.typeId = type.getId();
    }

    public List<String> getSkills() {
        return Collections.unmodifiableList(this.skills);
    }

    public void addSkill(String skill) {
        if (!this.skills.contains(skill))
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

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Integer getVacancies() {
        return vacancies;
    }

    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }

    public SystemStatus getStatus() {
        return SystemStatus.parse(this.statusId);
    }

    public void setStatus(SystemStatus status) {
        this.statusId = status.getId();
    }

    public List<Candidate> getCandidates() {
        return Collections.unmodifiableList(candidates);
    }
    
    public void subscribe(Candidate candidate) {
        this.candidates.add(candidate);
    }
    
    public void unsubscribe(Candidate candidate) {
        this.candidates.remove(candidate);
    }
    
    public void unsubscribeAll() {
        this.candidates = new ArrayList<>();
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Administrator getManager() {
        return manager;
    }

    public void setManager(Administrator manager) {
        this.manager = manager;
    }

    public Administrator getAppraiser() {
        return appraiser;
    }

    public void setAppraiser(Administrator appraiser) {
        this.appraiser = appraiser;
    }

    @Override
    public String toString() {
        return "Offer{" + "id=" + id + ", typeId=" + typeId + ", skills=" + skills + ", description=" + description + ", vacancies=" + vacancies + ", creationDateTime=" + creationDateTime + ", statusId=" + statusId + ", candidates=" + candidates + ", manager=" + manager + ", appraiser=" + appraiser + '}';
    }
}
