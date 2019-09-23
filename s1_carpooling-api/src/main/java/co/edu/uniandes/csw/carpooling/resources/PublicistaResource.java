/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.PublicistaDTO;
import co.edu.uniandes.csw.carpooling.ejb.PublicistaLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Santiago Ballesteros
 */
@Path("/publicista")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PublicistaResource {
   
        private static final Logger LOGGER = Logger.getLogger(PublicistaResource.class.getName());
        
        @Inject
        private PublicistaLogic logica;
        
        @POST
        public PublicistaDTO createPublicista(PublicistaDTO publicista){
            
            PublicistaEntity publicistaEntity = publicista.toEntity();
            publicistaEntity = logica.createPublicista(publicistaEntity);
            return new PublicistaDTO(publicistaEntity);
        
        }
}
