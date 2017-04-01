/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.UserDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.PasswordContentException;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */
@Stateless
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
    
    @Override
    public List<User> usersToApprove() {
        return userDAO.usersToApprove();
    }

    @Override
    public void changePassword(User user, String password, String confirmPassword) {

        try {
            validatePassword(password, confirmPassword);
            userDAO.updatePassword(user, password);
        } catch (PasswordContentException ex) {
            throw new EJBException(ex);
        }
    }
    
    private void validatePassword(String password, String confirmPassword) throws PasswordContentException {
        if (password == null || password.trim().equals("") || confirmPassword == null || confirmPassword.trim().equals("")) {
            throw new PasswordContentException("Please, enter valids password e confirmation");
        } else if (!password.equals(confirmPassword)) {
            throw new PasswordContentException("Error: Different values to password e confirmation password");
        }
    }
    
    
    
}
