/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface UserService {
    
    List<User> usersToApprove();
    
    void changePassword(User user, String password, String confirmPassword);
}
