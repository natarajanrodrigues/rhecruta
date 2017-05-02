/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
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
public class AppraiserBean {
    
    @Inject
    private AdministratorService adminService;
    
    private List<Administrator> appraiserList;
    
    @PostConstruct
    private void init() {
        listAllAppraisers();
    }
    
    public List<Administrator> getAllAppraisers() {
        return adminService.getAllAdministratorsByRole(Role.APPRAISER);
    }
    
    private void listAllAppraisers() {
        this.appraiserList = getAllAppraisers();
    }
}
