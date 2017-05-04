/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.jse;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Administrator;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Offer;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import br.edu.ifpb.dac.rhecruta.shared.domain.vo.Credentials;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class Main {
    
    public static void main(String[] args) {
        
        ServiceLocator locator = new ServiceLocator();
        AdministratorService admService = locator.lookup("java:global/rhecruta-core/AdministratorServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.AdministratorService"
                , AdministratorService.class);
        
        
        User user = new User(new Credentials("natarajan@ig.com.br", "natara"), true);
        user.setId(1L);
        user.setRole(Role.MANAGER);
        
        Administrator byUser = admService.getByUser(user);
        System.out.println(byUser);
        
        OfferService service = locator.lookup("java:global/rhecruta-core/OfferServiceImpl!br.edu.ifpb.dac.rhecruta.shared.interfaces.OfferService"
                , OfferService.class);
        
        List<Offer> byAdministrator = service.listAll();
        byAdministrator.forEach(System.out::println);
        
        
    }
}
