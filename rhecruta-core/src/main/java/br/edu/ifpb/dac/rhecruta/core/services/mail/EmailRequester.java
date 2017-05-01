/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.mail;

import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;


/**
 *
 * @author Pedro Arthur
 */
public interface EmailRequester {
    
    void send(Email email);
}
