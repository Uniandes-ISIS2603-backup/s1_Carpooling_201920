/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.TrayectoDTO;
import co.edu.uniandes.csw.carpooling.ejb.TrayectoLogic;
import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
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
@Path("trayectos")
@Produces("aplication/json")
@Consumes("aplication/json")
@RequestScoped
public class TrayectoResource {
    
    private static final Logger LOGGER = Logger.getLogger(ViajeResource.class.getName());
    
    @Inject
    private TrayectoLogic logic;
    
    @POST
    public TrayectoDTO createViaje(TrayectoDTO trayecto) throws BusinessLogicException{
        TrayectoEntity trayectoEntity =trayecto.toEntity();
        trayectoEntity = logic.createTrayecto(trayectoEntity);
        return new TrayectoDTO(trayectoEntity);
    }
    
}
