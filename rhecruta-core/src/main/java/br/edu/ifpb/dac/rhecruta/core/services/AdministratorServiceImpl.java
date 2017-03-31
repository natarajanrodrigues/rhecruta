/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.AdministratorDAO;
import br.edu.ifpb.dac.rhecruta.core.services.exceptions.EntityNotFoundException;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author Pedro Arthur
 */
@Stateless
@Remote(AdministratorService.class)
public class AdministratorServiceImpl implements AdministratorService {
    
    @EJB
    private AdministratorDAO administratorDAO;

    @Override
    public Administrator getByUser(User user) {
        return administratorDAO.getAdministratorByUser(user);
    }

    @Override
    public void save(Administrator administrator) {
        System.out.println("[AdministratorServiceImpl: "+administrator+"]");
        administratorDAO.save(administrator);
    }

    @Override
    public void update(Administrator administrator) {
        administratorDAO.update(administrator);
    }

    @Override
    public void delete(Administrator administrator) {
        try {
            Administrator found = administratorDAO.get(administrator.getId());
            administratorDAO.delete(found);
        } catch (NoResultException ex) {
            throw new EntityNotFoundException("You're trying to remove a non existent administrator.");
        }
    }

    @Override
    public void changeRole(Administrator administrator, Role newRole) {
        administrator.getUser().setRole(newRole);
        administratorDAO.update(administrator);
    }

    @Override
    public List<Administrator> listAdministratorsToApprove() {
        return administratorDAO.listAdministratorsToApprove();
    }

    @Override
    public void denyRequest(Administrator administrator) {
        //Notificate admin
        delete(administrator);
    }
    
}
