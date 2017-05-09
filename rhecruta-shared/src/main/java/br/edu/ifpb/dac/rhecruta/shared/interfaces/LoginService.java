/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Pedro Arthur
 */
public interface LoginService {
    
    User signIn(Credentials credentials);
}
