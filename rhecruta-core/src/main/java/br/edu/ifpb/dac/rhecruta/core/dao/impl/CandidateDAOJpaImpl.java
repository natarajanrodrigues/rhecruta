/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.impl;

import br.edu.ifpb.dac.rhecruta.core.dao.interfaces.CandidateDAO;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import java.util.Collections;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Arthur
 */

@Stateless
@Local(CandidateDAO.class)
public class CandidateDAOJpaImpl implements CandidateDAO {
    
    @PersistenceContext(unitName = "rhecruta-pu")
    private EntityManager manager;

    @Override
    public Candidate getCandidateByUser(User user) {
        try {
            TypedQuery<Candidate> query = manager
                .createQuery("SELECT c FROM Candidate c"
                + " WHERE c.user.id = :userId", Candidate.class)
                .setParameter("userId", user.getId());
            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
        
    }

    @Override
    public void delete(Candidate candidate) {
        manager.remove(candidate);
    }

    @Override
    public void save(Candidate candidate) {
        manager.persist(candidate);
    }

    @Override
    public void update(Candidate candidate) {
        manager.merge(candidate);
    }

    @Override
    public Candidate get(Long id) {
        TypedQuery<Candidate> query = manager
                .createQuery("SELECT c FROM Candidate c"
                + " WHERE c.id = :id", Candidate.class)
                .setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Candidate> listCandidatesToApprove() {
        try {
            Query query = manager
                    .createQuery("SELECT c FROM Candidate c WHERE c.user.approved = FALSE");

            List<Candidate> list = query.getResultList();

            if (list == null || list.isEmpty()) {
                return Collections.EMPTY_LIST;
            } else {
                return list;
            }

        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<Candidate> listApprovedCandidates() {
        TypedQuery<Candidate> query = manager
                .createQuery("SELECT c FROM Candidate c"
                        + " WHERE c.user.approved = true", Candidate.class);
        return query.getResultList();
    }

    @Override
    public Candidate getByCPF(String cpf) {
        try {
            TypedQuery<Candidate> query = this.manager
                .createQuery("SELECT c FROM Candidate c WHERE c.cpf = :cpf", 
                Candidate.class)
                .setParameter("cpf", cpf);
            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public Candidate getByEmail(String email) {
        try {
            TypedQuery<Candidate> query = this.manager
                .createQuery("SELECT c FROM Candidate c WHERE c.user.credentials.email = :email", 
                Candidate.class)
                .setParameter("email", email);
            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
    
}
