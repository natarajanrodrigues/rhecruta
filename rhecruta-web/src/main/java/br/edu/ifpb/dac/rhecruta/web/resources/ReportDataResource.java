/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.resources;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.EnterviewService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author natarajan
 */
@Path("report")
public class ReportDataResource {
    
    @Inject
    private OfferService offerService;
    
    @GET
    @Path("languages")
    public Response getReportLanguagues(){
        List<Offer> mothOffers = offerService.getMothOffers();
        return null;
        
        
    }
    
    
}
