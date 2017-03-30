/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.LoginDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.LoginService;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Pedro Arthur
 */

@Stateless
@Remote(LoginService.class)
public class LoginServiceImpl implements LoginService {
    
    @EJB
    private LoginDAO loginDAO;

    @Override
    public User signIn(Credentials credentials) {
        try {
            return getByCredentials(credentials);
        } catch(LoginException ex) {
            throw new EJBException(ex);
        }
    }
    
    private User getByCredentials(Credentials credentials) throws LoginException {
        try {
            //
            User user = loginDAO.signIn(credentials);
            if(!user.isApproved()) throw new LoginException("User not approved yet.");
                return user;
        } catch (Exception ex) {
            throw new LoginException("Invalid E-mail/Password.");
        }
    }
    
}
