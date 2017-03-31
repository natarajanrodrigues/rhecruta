/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.exceptions;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author natarajan
 */

public class PasswordContentException extends EJBException  {

    public PasswordContentException() {
    }

    public PasswordContentException(String message) {
        super(message);
    }
    
}
