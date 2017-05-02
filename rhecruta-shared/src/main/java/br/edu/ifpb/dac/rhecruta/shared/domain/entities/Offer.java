/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferStatus;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
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
    
    @Column(name = "status_id")
    private int statusId;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offer_candidates",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id"))
    private final List<Candidate> candidates;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offer_administrators",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "administrator_id"))
    private final List<Administrator> administrators;

    public Offer(Long id, OfferType type, List<String> skills, String description, Integer vacancies, OfferStatus status, List<Candidate> candidates, List<Administrator> administrators) {
        this.id = id;
        this.typeId = type.getId();
        this.skills = skills;
        this.description = description;
        this.vacancies = vacancies;
        this.statusId = status.getId();
        this.candidates = candidates;
        this.administrators = administrators;
    }
    
    public Offer() {   
        this.skills = new ArrayList<>();
        this.candidates = new ArrayList<>();
        this.administrators = new ArrayList<>();
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

    public Integer getVacancies() {
        return vacancies;
    }

    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }

    public OfferStatus getStatus() {
        return OfferStatus.parse(this.statusId);
    }

    public void setStatus(OfferStatus status) {
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
    
    public List<Administrator> getAdministrators() {
        return Collections.unmodifiableList(administrators);
    }
    
    public void addAdministrator(Administrator administrator) {
        this.administrators.add(administrator);
    }
    
    public void removeAdministrator(Administrator administrator) {
        this.administrators.remove(administrator);
    }

    @Override
    public String toString() {
        return "Offer{" + "id=" + id + ", type=" + getStatus() + ", skills=" + skills + ", description=" + description + ", vacancies=" + vacancies + ", status=" + getStatus() + '}';
    } 
}
