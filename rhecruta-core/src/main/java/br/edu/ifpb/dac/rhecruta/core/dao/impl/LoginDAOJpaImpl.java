/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.LoginDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur
 */

@Stateless
@Local(LoginDAO.class)
public class LoginDAOJpaImpl implements LoginDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager entityManager;

    @Override
    public User signIn(Credentials credentials) {
        //
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        System.out.println("LoginDAO [ Email: "+email+", Password: "+password+" ] ");
        //
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u"
                + " WHERE u.credentials.email = :email"
                + " AND u.credentials.password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password);
        
        return query.getSingleResult();
    }
    
}
