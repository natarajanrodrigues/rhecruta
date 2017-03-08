/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;

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
    
}
