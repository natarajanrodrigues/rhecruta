/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.dto;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.SystemEvaluation;
import java.io.Serializable;

/**
 *
 * @author Pedro Arthur
 */
public class EnterviewSystemEvaluationDTO implements Comparable<EnterviewSystemEvaluationDTO>, Serializable {
    
    private Enterview enterview;
    private SystemEvaluation systemEvaluation;

    public EnterviewSystemEvaluationDTO(Enterview enterview, SystemEvaluation systemEvaluation) {
        this.enterview = enterview;
        this.systemEvaluation = systemEvaluation;
    }

    public Enterview getEnterview() {
        return enterview;
    }

    public void setEnterview(Enterview enterview) {
        this.enterview = enterview;
    }

    public SystemEvaluation getSystemEvaluation() {
        return systemEvaluation;
    }

    public void setSystemEvaluation(SystemEvaluation systemEvaluation) {
        this.systemEvaluation = systemEvaluation;
    }
    
    public Double getFinalScore() {
        Double eScore = this.enterview.getScore();
        Double sScore = this.systemEvaluation.getScore();
        Double result = (eScore+sScore)/2;
        return result;
    }

    @Override
    public String toString() {
        return "EnterviewSystemEvaluationDTO{" + "enterview=" + enterview + ", systemEvaluation=" + systemEvaluation + '}';
    }

    @Override
    public int compareTo(EnterviewSystemEvaluationDTO o) {
        if(getFinalScore() < o.getFinalScore())
            return 1;
        if(getFinalScore() > o.getFinalScore())
            return -1;
        return 0;
    }
}
