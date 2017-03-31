/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.exceptions;

import javax.ejb.EJBException;

/**
 *
 * @author Pedro Arthur
 */
public class UniqueFieldException extends EJBException {
    
    public UniqueFieldException() {
        
    }
    
    public UniqueFieldException(String msg) {
        super(msg);
    }
}
