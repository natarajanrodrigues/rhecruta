/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.producers;

import br.edu.ifpb.dac.rhecruta.shared.interfaces.EvaluationService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.LoginService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Pedro Arthur
 */
@ApplicationScoped
public class EvaluationServiceProducer {
    
    private static final String RESOURCE = "java:global/rhecruta-core/EvaluationServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.EvaluationService";
    
    @Default
    @Dependent
    @Produces
    public EvaluationService getLoginService() {
        return new ServiceLocator().lookup(RESOURCE, EvaluationService.class);
    }
}
