/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;


import co.edu.uniandes.csw.carpooling.dtos.ViajeRecurrenteDTO;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Juan David Alarcón
 */
@Path("viajesRecurrentes")
@Produces("application/JSON")
@Consumes("application/JSON")
public class ViajeRecurrenteResource {
 
    private static final Logger LOGGER = Logger.getLogger(ViajeRecurrenteResource.class.getName());
    
    @POST
    public ViajeRecurrenteDTO createViajeRecurrente(ViajeRecurrenteDTO viajeRecurrente)
    {
        return viajeRecurrente;
    }
}   
