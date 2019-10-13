/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.NotificacionDTO;
import co.edu.uniandes.csw.carpooling.ejb.NotificacionLogic;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author le.perezl
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ViajeroNotificacionResource {
    
     private static final Logger LOGGER = Logger.getLogger(ConductorCalificacionResource.class.getName());
    @Inject NotificacionLogic notificacionLogic;
    
    @GET
    public List<NotificacionDTO> getNotificacionesByViajero(@PathParam("viajerosId") long viajerosId)
    {
        LOGGER.log(Level.INFO, "NotificacionResource getNotificaciones: input: {0}", viajerosId);
        List<NotificacionDTO> listaDTOs = listEntity2DTO(notificacionLogic.getNotificacionesByViajero(viajerosId));
        LOGGER.log(Level.INFO, "NotificacionResource getNotificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
       private List<NotificacionDTO> listEntity2DTO(List<NotificacionEntity> entityList) {
        List<NotificacionDTO> list = new ArrayList<NotificacionDTO>();
        for (NotificacionEntity entity : entityList) {
            list.add(new NotificacionDTO(entity));
        }
        return list;
    }
    @GET
    @Path("{notificacionesId: \\d+}")
    public NotificacionDTO getNotificacionByViajero(@PathParam("viajerosId") Long viajerosId, @PathParam("notificacionesId") Long notificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "NotificacionResource getNotificacion: input: {0}", notificacionesId);
        NotificacionEntity entity = notificacionLogic.getNotificacionByViajero(viajerosId, notificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /esto es en notificacion/" + viajerosId + "/notificaciones/" + notificacionesId + " no existe.", 404);
        }
        NotificacionDTO notificacionDTO = new NotificacionDTO(entity);
        LOGGER.log(Level.INFO, "NotificacionResource getNotificacion: output: {0}", notificacionDTO);
        return notificacionDTO;
    }
    
        
    @DELETE
    @Path("{notificacionesId: \\d+}")
    public void deleteNotificacionByViajero(@PathParam("viajerosId") Long viajerosId, @PathParam("notificacionesId") Long notificacionesId) throws BusinessLogicException {
        NotificacionEntity entity = notificacionLogic.getNotificacionByViajero(viajerosId, notificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + viajerosId + "/notificaciones/" + notificacionesId + " no existe.", 404);
        }
        notificacionLogic.deleteNotificacionByViajero(viajerosId, notificacionesId);
    }
}
