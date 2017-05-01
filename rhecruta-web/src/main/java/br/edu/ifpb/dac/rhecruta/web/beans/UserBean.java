/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class UserBean {
    
    @Inject
    private UserService userService;
    
    public List<User> getUsersToApprove() {
        return userService.usersToApprove();
    }
    
    public int getUsersToApproveCount() {
        return userService.usersToApprove().size();
    }
    
   
}
