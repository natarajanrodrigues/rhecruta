/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.producers;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.CurriculumService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Pedro Arthur
 */

@ApplicationScoped
public class CurriculumServiceProducer {
    
    private final String resource = "java:global/rhecruta-core/CurriculumServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.CurriculumService";
    
    @Dependent
    @Produces
    @Default
    public CurriculumService getCurriculumService() {
        return new ServiceLocator().lookup(resource, CurriculumService.class);
    }
}
