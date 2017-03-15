/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.UserDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import javax.ejb.EJB;
import javax.ejb.Remote;

/**
 *
 * @author Pedro Arthur
 */
@Remote(UserService.class)
public class UserServiceImpl implements UserService {
    
    @EJB
    private UserDAO userDAO;
    
    /**
     * Approves a register solicitation and notifies the user about the register approval or denial
     * @param user 
     * @param approved 
     */
    @Override
    public void evaluateSignUpRequest(User user, Boolean approved) {
        userDAO.evaluateSignUpRequest(user, approved);
        //Send Email
    }
    
}
