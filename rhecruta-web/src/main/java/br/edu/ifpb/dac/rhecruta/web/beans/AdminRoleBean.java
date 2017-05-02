/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class AdminRoleBean {
    
    @Inject
    private AdministratorService adminService;
    
    private List<Administrator> administratorList;
    
    @PostConstruct
    private void init() {
        System.out.println("[AdminRoleBean] CREATED!");
        listAllAdministrators();
    }
    
    public List<Administrator> getAllAdministrators() {
        return adminService.getAllAdministrators();
    }
    
    private void listAllAdministrators() {
        this.administratorList = getAllAdministrators();
    }

    public List<Administrator> getAdministratorsList() {
        return administratorList;
    }

    public void setAdministratorsList(List<Administrator> appraiserList) {
        this.administratorList = appraiserList;
    }
    
    public String changeToManager(Administrator appraiser) {
        adminService.changeRole(appraiser, Role.MANAGER);
        listAllAdministrators();
        return null;
    }
    
    public String chanteToAppraiser(Administrator manager) {
        adminService.changeRole(manager, Role.APPRAISER);
        listAllAdministrators();
        return null;
    }
    
    public boolean isManager(Administrator admin) {
        Role role = admin.getUser().getRole();
        return role.equals(Role.MANAGER);
    }
}
