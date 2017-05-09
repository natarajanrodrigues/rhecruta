/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.AdministratorDAO;
import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.EnterviewDAO;
import br.edu.ifpb.dac.rhecruta.core.services.mail.EmailRequester;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.EntityNotFoundException;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Email;
import br.edu.ifpb.dac.rhecruta.shared.exceptions.UniqueFieldException;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    @EJB
    private EnterviewDAO enterviewDAO;
    @EJB
    private UserService userService;
    
    @Override
    public Administrator getByUser(User user) {
        return administratorDAO.getAdministratorByUser(user);
    }

    @Override
    public void save(Administrator administrator) {
        try {
            System.out.println("[AdministratorServiceImpl: " + administrator + "]");
            validateAdministrator(administrator);
            administratorDAO.save(administrator);
        } catch(UniqueFieldException | IllegalArgumentException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    private void validateAdministrator(Administrator administrator) throws UniqueFieldException {
        //procurar por administrator com a mesmo email
        Administrator byEmail = administratorDAO.getByEmail(administrator.getUser().getCredentials().getEmail());
        if (byEmail != null)
            throw new UniqueFieldException("A user with this email already exists");
        //procurar por administrator com mesmo cpf
        Administrator byCPF = administratorDAO.getByCPF(administrator.getCpf());
        if (byCPF != null) 
            throw new IllegalArgumentException("A user with this CPF already exists");
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
        try {
            verifyChangeRole(administrator, newRole);
            administrator.getUser().setRole(newRole);
            administratorDAO.update(administrator);
        } catch (IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
    }
    
    private void verifyChangeRole(Administrator administrator, Role newRole) {
        if(newRole.equals(Role.MANAGER)) {
            List<Enterview> enterview = enterviewDAO.listByAppraiser(administrator)
                    .stream().filter((e) -> !e.isFinished()).collect(Collectors.toList());

            if(!enterview.isEmpty())
                throw new IllegalArgumentException("You can't change your role to Manager "
                        + "'cause you have non finished interviews. Wait all your interviews end "
                        + "and try again.");
        }
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

    @Override
    public Administrator getByCPF(String cpf) {
        return this.administratorDAO.getByCPF(cpf);
    }

}
