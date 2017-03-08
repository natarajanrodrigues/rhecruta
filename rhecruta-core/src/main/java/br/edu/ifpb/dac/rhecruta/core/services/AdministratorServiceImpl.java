/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.AdministratorDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.UserDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import javax.ejb.EJB;
import javax.ejb.Remote;

/**
 *
 * @author Pedro Arthur
 */
@Remote(AdministratorService.class)
public class AdministratorServiceImpl implements AdministratorService {
    
    @EJB
    private AdministratorDAO administratorDAO;
    @EJB
    private UserDAO userDAO;

    @Override
    public Administrator getByUser(User user) {
        return administratorDAO.getAdministratorByUser(user);
    }

    @Override
    public void save(Administrator administrator) {
        userDAO.save(administrator.getUser());
        administratorDAO.save(administrator);
    }

    @Override
    public void update(Administrator administrator) {
        userDAO.update(administrator.getUser());
        administratorDAO.update(administrator);
    }

    @Override
    public void delete(Administrator administrator) {
        administratorDAO.delete(administrator);
    }

    @Override
    public void changeRole(Administrator administrator, Role newRole) {
        administratorDAO.changeRole(administrator, newRole);
    }
    
}
