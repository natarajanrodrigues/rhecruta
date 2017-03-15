/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

/**
 *
 * @author Pedro Arthur
 * @param <T>
 */
public interface DAO<T> {
    
    void save(T obj);
    void update(T obj);
    
}
