/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain;

/**
 *
 * @author Pedro Arthur
 */
public class SystemEvaluation {
    
    private Double score;
    private Candidate candidate;
    private Offer offer;

    public SystemEvaluation(Double score, Candidate candidate, Offer offer) {
        this.score = score;
        this.candidate = candidate;
        this.offer = offer;
    } 

    public SystemEvaluation() {
    
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "SystemEvaluation{" + "score=" + score + ", candidate=" + candidate + ", offer=" + offer + '}';
    }
}
