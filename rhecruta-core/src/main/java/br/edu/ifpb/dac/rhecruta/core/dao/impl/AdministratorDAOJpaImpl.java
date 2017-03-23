/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.AdministratorDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
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
@Local(AdministratorDAO.class)
public class AdministratorDAOJpaImpl implements AdministratorDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager manager;

    @Override
    public Administrator getAdministratorByUser(User user) {
        TypedQuery<Administrator> query = manager
                .createQuery("SELECT a FROM Administrator a"
                + " WHERE a.user.id = :userId", Administrator.class)
                .setParameter("userId", user.getId());
        return query.getSingleResult();
    }

    @Override
    public void delete(Administrator administrator) {
        Administrator found = manager.find(Administrator.class, administrator.getId());
        manager.remove(found);
    }

    @Override
    public void save(Administrator obj) {
        manager.persist(obj);
    }

    @Override
    public void update(Administrator updatedAdmin) {
        manager.merge(updatedAdmin);
    }

    @Override
    public Administrator get(Long id) {
        TypedQuery<Administrator> query = manager
                .createQuery("SELECT a FROM Administrator a"
                + " WHERE a.id = :id", Administrator.class)
                .setParameter("userId", id);
        return query.getSingleResult();
    }
    
}
