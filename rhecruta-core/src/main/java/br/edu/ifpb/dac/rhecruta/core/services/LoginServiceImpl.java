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
            User found = getByCredentials(credentials);
            return found;
        } catch (LoginException ex) {
            System.out.println("Catched LoginException, throwing EJBException with the LoginException wrapped!");
            throw new EJBException(ex.getMessage());
        }
    }

    private User getByCredentials(Credentials credentials) throws LoginException {
        User user = loginDAO.signIn(credentials);
        if (user == null) {
            throw new LoginException("Invalid E-mail/Password.");
        }
        if (!user.isApproved()) {
            throw new LoginException("User wasn't approved yet.");
        }
        return user;
    }

}
