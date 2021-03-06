/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.resources;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
@Path("skill")
@Stateless
public class ReportSkillResource {

    @Inject
    private OfferService offerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerLanguagues() {

        Object[] monthOffersByLanguage = offerService.getMonthOffersByLanguage();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Skill");
        JsonArray data = new JsonArray();

        for (Object o : monthOffersByLanguage) {

            Object[] arrayO = (Object[]) o;
            JsonObject json = new JsonObject();
            json.addProperty("name", (String) arrayO[0]);
            json.addProperty("y", (Long) arrayO[1]);
            data.add(json);
        }

        jsonObject.add("data", data);

        return Response.ok().entity(jsonObject.toString()).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("vacancy")
    public Response getVacancyPerLanguagues() {

        Object[] monthOffersByLanguage = offerService.getMonthCandidatesPerVacancyBySkill();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Skill");
        JsonArray data = new JsonArray();

        for (Object o : monthOffersByLanguage) {

            Object[] arrayO = (Object[]) o;
            JsonObject json = new JsonObject();
            json.addProperty("name", (String) arrayO[0]);
            json.addProperty("y", (Long) arrayO[1]);
            data.add(json);
        }

        jsonObject.add("data", data);

        return Response.ok().entity(jsonObject.toString()).build();

    }

}
