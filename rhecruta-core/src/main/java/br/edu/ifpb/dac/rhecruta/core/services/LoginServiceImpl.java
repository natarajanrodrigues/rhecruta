/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.LoginDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.domain.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.LoginService;
import javax.ejb.EJB;
import javax.ejb.Remote;

/**
 *
 * @author Pedro Arthur
 */


@Remote(LoginService.class)
public class LoginServiceImpl implements LoginService {
    
    @EJB
    private LoginDAO loginDAO;

    @Override
    public User signIn(Credentials credentials) {
        return loginDAO.signIn(credentials);
    }
    
}
