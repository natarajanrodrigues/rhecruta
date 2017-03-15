/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.AdministratorDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pedro Arthur
 */

@Local(AdministratorDAO.class)
public class AdministratorDAOJpaImpl implements AdministratorDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager manager;

    @Override
    public Administrator getAdministratorByUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*@Override
    public void changeRole(Administrator administrator, Role newRole) {
        manager.getTransaction().begin();
        Administrator found = manager.find(Administrator.class, administrator.getId());
        User user = found.getUser();
        user.setRole(newRole);
        manager.getTransaction().commit();  
    }*/

    @Override
    public void delete(Administrator administrator) {
        manager.getTransaction().begin();
        Administrator found = manager.find(Administrator.class, administrator.getId());
        manager.remove(found);
        manager.getTransaction().commit();
    }

    @Override
    public void save(Administrator obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    @Override
    public void update(Administrator updatedAdmin) {
        
        manager.getTransaction().begin();
        
        manager.merge(updatedAdmin);
        //what about the role? :x
        
        manager.getTransaction().commit();
    }
    
}
