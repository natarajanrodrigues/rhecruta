/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.entities.evaluation;

import java.io.Serializable;
/**
 *
 * @author natarajan
 */

public class RankedUser implements Serializable{
    
    private Long idUser;
    private double ranking;
    private Long rankingPosition;

    public RankedUser() {
    }

    public RankedUser(Long idUser, double ranking, Long rankingPosition) {
        this.idUser = idUser;
        this.ranking = ranking;
        this.rankingPosition = rankingPosition;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public Long getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(Long rankingPosition) {
        this.rankingPosition = rankingPosition;
    }
    
    
}
