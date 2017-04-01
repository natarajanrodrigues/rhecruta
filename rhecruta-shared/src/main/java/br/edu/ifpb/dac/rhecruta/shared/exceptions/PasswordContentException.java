/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.exceptions;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;

/**
 *
 * @author natarajan
 */

public class PasswordContentException extends Exception {

    public PasswordContentException() {
    }

    public PasswordContentException(String message) {
        super(message);
    }
    
}
