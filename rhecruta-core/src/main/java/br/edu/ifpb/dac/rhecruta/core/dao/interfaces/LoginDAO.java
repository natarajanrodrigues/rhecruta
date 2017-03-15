/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.dao.interfaces;

import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;

/**
 *
 * @author Pedro Arthur
 */
public interface LoginDAO {
    
    User signIn(Credentials credentials);
}
