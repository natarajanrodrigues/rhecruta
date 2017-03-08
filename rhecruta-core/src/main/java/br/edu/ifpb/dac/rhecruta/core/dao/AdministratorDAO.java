/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao;

import br.edu.ifpb.dac.rhecruta.shared.domain.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;

/**
 *
 * @author Pedro Arthur
 */
public interface AdministratorDAO extends DAO<Administrator> {
    
    Administrator getAdministratorByUser(User user);
    void changeRole(Administrator administrator, Role newRole);
    void delete(Administrator administrator);
}
