/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.producers;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Pedro Arthur
 */

@ApplicationScoped
public class EnterviewServiceProducer {
    
    private final String resource = "java:global/rhecruta-core/EnterviewServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService";
    
    @Dependent
    @Produces
    @Default
    public EnterviewService getEnterviewService() {
        return new ServiceLocator().lookup(resource, EnterviewService.class);
    }
}
