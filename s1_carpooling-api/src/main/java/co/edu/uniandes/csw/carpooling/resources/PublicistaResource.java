/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.PublicistaDTO;
import co.edu.uniandes.csw.carpooling.dtos.PublicistaDetailDTO;
import co.edu.uniandes.csw.carpooling.ejb.PublicistaLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
        public PublicistaDTO createPublicista(PublicistaDTO publicista)throws BusinessLogicException{
            
            PublicistaEntity publicistaEntity = publicista.toEntity();
            publicistaEntity = logica.createPublicista(publicistaEntity);
            return new PublicistaDTO(publicistaEntity);
        
        }
        
        @GET
        @Path("{publicistaId: \\d+}")
        public PublicistaDetailDTO getEditorial(@PathParam("publicistaId") Long publicistaId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "PublicistaResource getPublicista: input: {0}", publicistaId);
        // Me devuelve la entidad por su id
        PublicistaEntity publicistaEntity = logica.getPublicista(publicistaId);
        
        // Si el objeto no existe significa que es nulo
        if (publicistaEntity == null) {
            throw new WebApplicationException("El recurso /publicista/" + publicistaId + " no existe.", 404);
        }
        PublicistaDetailDTO detailDTO = new PublicistaDetailDTO(publicistaEntity);
        LOGGER.log(Level.INFO, "PublicistaResource getPublicista: output: {0}", detailDTO);
        return detailDTO;
        }
}
