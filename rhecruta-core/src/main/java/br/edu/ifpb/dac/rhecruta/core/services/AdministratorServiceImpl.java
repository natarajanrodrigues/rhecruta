/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.AdministratorDAO;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.EntityNotFoundException;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    @EJB
    private EmailRequester emailRequester;

    @Override
    public Administrator getByUser(User user) {
        return administratorDAO.getAdministratorByUser(user);
    }

    @Override
    public void save(Administrator administrator) {
        System.out.println("[AdministratorServiceImpl: " + administrator + "]");
        administratorDAO.save(administrator);
    }

    @Override
    public void update(Administrator administrator) {
        administratorDAO.update(administrator);
    }

    @Override
    public void delete(Administrator administrator) {
        try {
            Administrator found = find(administrator.getId());
            administratorDAO.delete(found);
        } catch (EntityNotFoundException ex) {
            throw new EJBException(ex);
        }
    }

    private Administrator find(Long id) throws EntityNotFoundException {
        try {
            Administrator found = administratorDAO.get(id);
            return found;
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
    public void respondRequest(Administrator administrator, boolean approved) {
        Email createdEmail = createEmail(administrator, approved);
        if (!approved) {
            delete(administrator);
        } else {
            administrator.getUser().setApproved(true);
            update(administrator);
        }
        emailRequester.send(createdEmail);
    }

    private Email createEmail(Administrator administrator, boolean approved) {
        String message;
        if (approved) {
            message = "Olá " + administrator.getFirstname() + ", estamos felizes em afirmar"
                    + " que sua solicitação de cadastro no site Rhecruta foi aprovada.";
        } else {
            message = "Olá " + administrator.getFirstname() + ", sentimos informar"
                    + " que sua solicitação de cadastro no site Rhecruta foi negada.";
        }
        Email email = new Email();
        email.setFrom("rhecrutapp@gmail.com");
        email.setTo(administrator.getUser().getCredentials().getEmail());
        email.setRequestedDate(LocalDateTime.now());
        email.setSubject("Sobre sua solicitação de cadastro - Rhecruta");
        email.setText(message);
        return email;
    }

    @Override
    public List<Administrator> getAllAdministrators() {
        return administratorDAO.getAll();
    }

    @Override
    public List<Administrator> getAllAdministratorsByRole(Role role) {
        return this.administratorDAO.getAllByRole(role);
    }

}
