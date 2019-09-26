/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ConductorDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
    
    @POST
    public ConductorDTO createConductor(ConductorDTO conductor){
        
        return conductor;
    }
}
