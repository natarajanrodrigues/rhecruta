/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.enums.OfferType;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Natarajan Rodrigues
 */

@Named
@ApplicationScoped
public class EnumsBeans implements Serializable {

    public Role[] getAdminRoles(){
        return new Role[] {Role.APPRAISER, Role.MANAGER};
    }
    
    public OfferType[] getOfferTypes(){
        return new OfferType[] {OfferType.OPEN, OfferType.INVITE };
    }
    
}
