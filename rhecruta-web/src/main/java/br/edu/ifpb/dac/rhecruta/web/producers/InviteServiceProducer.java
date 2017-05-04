/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.producers;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.InviteService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Pedro Arthur
 */

@ApplicationScoped
public class InviteServiceProducer {
    
    private final String resource = "java:global/rhecruta-core/InviteServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.InviteService";
    
    @Dependent
    @Default
    @Produces
    public InviteService getInviteService() {
        return new ServiceLocator().lookup(resource, InviteService.class);
    }
}
