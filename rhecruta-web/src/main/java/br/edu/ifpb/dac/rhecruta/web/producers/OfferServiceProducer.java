/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.producers;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Pedro Arthur
 */

@ApplicationScoped
public class OfferServiceProducer {
    
    private static final String RESOURCE = "java:global/rhecruta-core/OfferServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService";
    
    @Dependent
    @Produces
    @Default
    private OfferService getOfferService() {
        return new ServiceLocator().lookup(RESOURCE, OfferService.class);
    }
}
