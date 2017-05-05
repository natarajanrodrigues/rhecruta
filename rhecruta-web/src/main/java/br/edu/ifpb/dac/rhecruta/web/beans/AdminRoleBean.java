/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.web.utils.SessionUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

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

    @Inject
    private User loggedUser;

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
        try {
            adminService.changeRole(appraiser, Role.MANAGER);
            listAllAdministrators();

            addMessage("adminRoleMsg",
                    createMessage("Role successfully changed!",
                            FacesMessage.SEVERITY_INFO));

        } catch (EJBException ex) {
            addMessage("adminRoleMsg",
                    createMessage(ex.getCausedByException().getMessage(),
                            FacesMessage.SEVERITY_ERROR));
        }
        return null;
    }

    public String chanteToAppraiser(Administrator manager) {
        adminService.changeRole(manager, Role.APPRAISER);

        if (loggedUser.getCredentials().authenticate(manager.getUser().getCredentials())) {
            addMessage("adminRoleMsg",
                    createMessage("Role successfully changed!",
                            FacesMessage.SEVERITY_INFO));
            SessionUtils.invalidate();
            return "/index.xhtml?faces-redirect=true&roleChanged=true";
        } else {
            listAllAdministrators();
            return null;
        }
    }

    public boolean isManager(Administrator admin) {
        Role role = admin.getUser().getRole();
        return role.equals(Role.MANAGER);
    }

    private FacesMessage createMessage(String text, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(text);
        message.setSeverity(severity);
        return message;
    }

    private void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
}
