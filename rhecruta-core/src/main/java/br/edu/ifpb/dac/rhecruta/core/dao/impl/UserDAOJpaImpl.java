/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.UserDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur && Natarajan Rodrigues
 * 
 */
@Stateless
@Local(UserDAO.class)
public class UserDAOJpaImpl implements UserDAO {
     
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager manager;

    @Override
    public void evaluateSignUpRequest(User user, Boolean approved) {
        User found = manager.find(User.class, user.getId());
        if(approved)
            found.setApproved(approved);
        else
            manager.remove(found);
    }
    
    
    @Override
    public List<User> usersToApprove() {
        
        System.out.println("Getting users do Approve ");
        //
        
        Query query = manager
                .createQuery("SELECT u FROM User u"
                + " WHERE u.approved = false");
        
        List<User> list = query.getResultList();
        
        if (list == null || list.isEmpty() )
            return Collections.EMPTY_LIST;
        else return list;
        
    }

    @Override
    public void updatePassword(User user, String password) {
        User found = manager.find(User.class, user.getId());
        Credentials credentials = found.getCredentials();
        credentials.setPassword(password);
        found.setCredentials(credentials);
    }

    @Override
    public List<User> searchByEmail(String email) {
        
        TypedQuery<User> query = manager
                .createQuery("SELECT u FROM User u"
                + " WHERE u.credentials.email = :email", User.class)
                .setParameter("email", email);
        
        List<User> list = query.getResultList();
        
        if (list == null || list.isEmpty() )
            return Collections.EMPTY_LIST;
        else return list;
    }
    
    
    
    
}
