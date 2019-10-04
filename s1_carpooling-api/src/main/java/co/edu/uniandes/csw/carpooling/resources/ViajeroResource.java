/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ViajeroDTO;
import co.edu.uniandes.csw.carpooling.ejb.ViajeroLogic;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.POST;

/**
 *
 * @author Santiago Ballesteros
 */
public class ViajeroResource {
     private static final Logger LOGGER = Logger.getLogger(ViajeroResource.class.getName());
    
    @Inject
    private ViajeroLogic logica;
    
    @POST
    public ViajeroDTO createViajero(ViajeroDTO viajero){
        /*ViajeroEntity viajeroEntity = viajero.toEntity(viajero);
        viajeroEntity = logica.createViajero(viajeroEntity);
        return new ViajeroDTO(viajeroEntity);*/
        return viajero;
    }
    
}
