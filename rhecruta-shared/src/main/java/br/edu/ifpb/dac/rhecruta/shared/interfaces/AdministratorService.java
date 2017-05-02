/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface AdministratorService {
    
    Administrator getByUser(User user);
    void save(Administrator administrator);
    void update(Administrator administrator);
    void delete(Administrator administrator);
    void changeRole(Administrator administrator, Role newRole);
    List<Administrator> listAdministratorsToApprove();
    void respondRequest(Administrator administrator, boolean approve);
    List<Administrator> getAllAdministrators();
    List<Administrator> getAllAdministratorsByRole(Role role);
    
}
