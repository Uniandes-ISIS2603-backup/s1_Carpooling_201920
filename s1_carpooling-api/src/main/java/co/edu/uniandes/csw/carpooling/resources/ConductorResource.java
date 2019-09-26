/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ConductorDTO;
import co.edu.uniandes.csw.carpooling.ejb.ConductorLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Nicolás Fajardo Ramirez
 */

@Path("conductores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ConductorResource {
    
    private static final Logger LOGGER = Logger.getLogger(ConductorResource.class.getName());
    
    @Inject
    private ConductorLogic logica;
    
    @POST
    public ConductorDTO createConductor(ConductorDTO conductor){
        /*ConductorEntity conductorEntity = conductor.toEntity(conductor);
        conductorEntity = logica.createConductor(conductorEntity);
        return new ConductorDTO(conductorEntity);*/
        return conductor;
    }
}
