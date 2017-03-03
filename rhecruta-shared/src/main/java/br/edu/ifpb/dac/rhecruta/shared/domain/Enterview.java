/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain;

import java.time.LocalDateTime;

/**
 *
 * @author Pedro Arthur
 */
public class Enterview {
    
    private Long id;
    private Offer offer;
    private CandidateUser candidate;
    private LocalDateTime start;
    private LocalDateTime end;
    private Address address;
    private AdministrativeUser applier;
    private Double score;

    public Enterview(Long id, Offer offer, CandidateUser candidate, LocalDateTime start, LocalDateTime end, Address address, AdministrativeUser applier, Double score) {
        this.id = id;
        this.offer = offer;
        this.candidate = candidate;
        this.start = start;
        this.end = end;
        this.address = address;
        this.applier = applier;
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

    public CandidateUser getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateUser candidate) {
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

    public AdministrativeUser getApplier() {
        return applier;
    }

    public void setApplier(AdministrativeUser applier) {
        this.applier = applier;
    }
    
    public Double getScore() {
        return this.score;
    }
    
    public boolean isFinished() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(this.end);
    }

    @Override
    public String toString() {
        return "Enterview{" + "id=" + id + ", offer=" + offer + ", candidate=" + candidate + ", start=" + start + ", end=" + end + ", address=" + address + ", applier=" + applier + '}';
    }
}
