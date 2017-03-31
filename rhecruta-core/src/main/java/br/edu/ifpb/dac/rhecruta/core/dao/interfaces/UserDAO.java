/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface UserDAO {
    
    void evaluateSignUpRequest(User user, Boolean approved);
    
    List<User> usersToApprove();
    
    void updatePassword(User user, String password);
    
}
