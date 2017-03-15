/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.UserDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pedro Arthur
 * 
 */

@Local(UserDAO.class)
public class UserDAOJpaImpl implements UserDAO {
     
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager manager;

    @Override
    public void evaluateSignUpRequest(User user, Boolean approved) {
        manager.getTransaction().begin();
        User found = manager.find(User.class, user.getId());
        found.setApproved(approved);
        manager.getTransaction().commit();
    }
    
}
