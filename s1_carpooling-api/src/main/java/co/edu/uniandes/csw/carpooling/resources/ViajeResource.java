/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ViajeDTO;
import co.edu.uniandes.csw.carpooling.ejb.ViajeLogic;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Estudiante
 */
@Path("viajes")
@Produces("aplication/json")
@Consumes("aplication/json")
@RequestScoped
public class ViajeResource {
    
    private static final Logger LOGGER = Logger.getLogger(ViajeResource.class.getName());
    
    @Inject
    private ViajeLogic logic;
    
   @POST
    public ViajeDTO createViaje(ViajeDTO viaje) throws BusinessLogicException{
        ViajeEntity viajeEntity =viaje.toEntity();
        viajeEntity = logic.createViaje(viajeEntity);
        return new ViajeDTO(viajeEntity);
    }
    
}
