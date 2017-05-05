/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.resources;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author natarajan
 */
@Path("offer")
@Stateless
public class ReportOfferResource {
    
    @Inject
    private OfferService offerService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("daily")
    public Response getPerDay(){
        
        List<Offer> monthOffers = offerService.getMonthOffers();
        
        Map<LocalDate, List<Offer>> collect = monthOffers
                .stream()
                .collect(Collectors.groupingBy( i -> i.getCreationDateTime().toLocalDate()));
        
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        
        LocalDate now = LocalDate.now();
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= dayOfMonth; i++) {
            Integer total = null;
            
            LocalDate l = LocalDate.of(now.getYear(), now.getMonth(), i);
            List<Offer> get = collect.get(l);
            if (get != null)
                result.add(get.size());
            else
                result.add(0);
        }
        Integer[] resultArray = new Integer[result.size()];
        
        result.toArray(resultArray);
        Serie serie = new Serie("Number of Offers per Day", resultArray);
        
        return Response.ok().entity(serie).build();
           
    }
    
    
    
    
}
