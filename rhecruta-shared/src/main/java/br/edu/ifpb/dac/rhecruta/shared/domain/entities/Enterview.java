/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities;

import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Address;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Pedro Arthur
 */
@Entity
public class Enterview implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
    
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
    
    @Column(name = "start_time")
    private LocalDateTime start;
    
    @Column(name = "end_time")
    private LocalDateTime end;
    
    @Embedded
    private Address address;
    
    private Double score;

    public Enterview(Long id, Offer offer, Candidate candidate, LocalDateTime start, LocalDateTime end, Address address, Double score) {
        this.id = id;
        this.offer = offer;
        this.candidate = candidate;
        this.start = start;
        this.end = end;
        this.address = address;
        this.score = score;
    }
    
    public Enterview() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Double getScore() {
        return this.score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
    
    public boolean isFinished() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(this.end);
    }

    @Override
    public String toString() {
        return "Enterview{" + "id=" + id + ", offer=" + offer + ", candidate=" + candidate + ", start=" + start + ", end=" + end + ", address=" + address + ", score=" + score + '}';
    }
}
