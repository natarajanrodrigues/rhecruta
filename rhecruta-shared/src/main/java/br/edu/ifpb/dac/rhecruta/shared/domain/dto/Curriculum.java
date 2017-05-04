/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.dto;

import java.io.Serializable;

/**
 *
 * @author Pedro Arthur
 */
public class Curriculum implements Serializable {
    
    private Long candidateId;
    private String filename;
    private byte[] bytes;

    public Curriculum(Long candidateId, String filename, byte[] bytes) {
        this.candidateId = candidateId;
        this.filename = filename;
        this.bytes = bytes;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "Curriculum{" + "candidateId=" + candidateId + ", filename=" + filename + ", bytes=" + bytes + '}';
    }
}
