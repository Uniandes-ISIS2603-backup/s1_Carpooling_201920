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
@Path("/notificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConductorNotificacionResource {
    
    private static final Logger LOGGER = Logger.getLogger(ConductorNotificacionResource.class.getName());
    @Inject 
    NotificacionLogic notificacionLogic;
    
    @GET
    public List<NotificacionDTO> getNotificacionesByConductor(@PathParam("conductoresId") long conductoresId)
    {
        LOGGER.log(Level.INFO, "NotificacionResource getNotificaciones: input: {0}", conductoresId);
        List<NotificacionDTO> listaDTOs = listEntity2DTO(notificacionLogic.getNotificacionesByConductor(conductoresId));
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
    public NotificacionDTO getNotificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("notificacionesId") Long notificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "NotificacionResource getNotificacion: input: {0}", notificacionesId);
        NotificacionEntity entity = notificacionLogic.getNotificacionByConductor(conductoresId, notificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /esto es en notificacion/" + conductoresId + "/notificaciones/" + notificacionesId + " no existe.", 404);
        }
        NotificacionDTO notificacionDTO = new NotificacionDTO(entity);
        LOGGER.log(Level.INFO, "NotificacionResource getNotificacion: output: {0}", notificacionDTO);
        return notificacionDTO;
    }
    
        
    @DELETE
    @Path("{notificacionesId: \\d+}")
    public void deleteNotificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("notificacionesId") Long notificacionesId) throws BusinessLogicException {
        NotificacionEntity entity = notificacionLogic.getNotificacionByConductor(conductoresId, notificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/notificaciones/" + notificacionesId + " no existe.", 404);
        }
        notificacionLogic.deleteNotificacionByConductor(conductoresId, notificacionesId);
    }
}
