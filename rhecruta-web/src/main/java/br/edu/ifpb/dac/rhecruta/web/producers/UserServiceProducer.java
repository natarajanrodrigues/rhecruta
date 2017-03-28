/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.producers;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Natarajan Rodrigues
 */

@ApplicationScoped
public class UserServiceProducer {
    
    
//    java:global/rhecruta-core/UserServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService, java:global/rhecruta-core/UserServiceImpl
    private static final String RESOURCE = "java:global/rhecruta-core/UserServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.UserService";
    
    @Dependent
    @Produces
    @Default
    private UserService getUserService() {
        return new ServiceLocator().lookup(RESOURCE, UserService.class);
    }
}
