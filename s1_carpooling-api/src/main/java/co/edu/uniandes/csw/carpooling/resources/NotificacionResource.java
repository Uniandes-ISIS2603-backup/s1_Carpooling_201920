/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.NotificacionDTO;
import co.edu.uniandes.csw.carpooling.dtos.ReservaDTO;
import co.edu.uniandes.csw.carpooling.ejb.NotificacionLogic;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@RequestScoped
public class NotificacionResource {
    
     private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(ReservaResource.class.getName());
    
    @Inject
    private NotificacionLogic logic;
    
    @POST
    public NotificacionDTO createNotificacion(NotificacionDTO notificacion) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "NotificacionResource createNotificacion: input: {0}", notificacion);
        NotificacionEntity notificacionEntity =notificacion.toEntity();
        notificacionEntity = logic.createNotificacion(notificacionEntity);
        return notificacion;
    }
   
//    @GET
//    public List<ReservaDTO> getReservas(){
//        LOGGER.info("rESERVAResource getReservas: input: void");
//        List<ReservaDTO> reserva = listViajesEntityToDTO(logic.findReservas());
//        LOGGER.log(Level.INFO, "ViajeResource getViajes: output: {0}", viajes);
//        return RESERVA;
//    } 
    @GET
    @Path("{notificacionesId: \\d+}")
    public NotificacionDTO getNotificacion(@PathParam("notificacionId") Long notificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "NotificacionResource getViaje: input: {0}", notificacionId);
        NotificacionEntity notificacionEntity = logic.findNotificacion(notificacionId);
        if (notificacionEntity == null) {
            throw new WebApplicationException("El recurso /notificacion/" + notificacionId + " no existe.", 404);
        }
        NotificacionDTO detailDTO = new NotificacionDTO(notificacionEntity);
        LOGGER.log(Level.INFO, "ReservaResource getReserva: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @PUT
    @Path("{notificacionId: \\d+}")
    public NotificacionDTO updateNotificacion(@PathParam("reservasId") Long notificacionId, NotificacionDTO notificacion) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "NotificacionResource updatNotificacion: input: notificacionId: {0} , author: {1}", new Object[]{notificacionId, notificacion});
        notificacion.setId(notificacionId);
        if (logic.findNotificacion(notificacionId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + notificacionId + " no existe.", 404);
        }
        NotificacionDTO detailDTO = new NotificacionDTO(logic.updateNotificacion(notificacionId, notificacion.toEntity()));
        LOGGER.log(Level.INFO, "NotificacionResource updateNotificacion: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{notificacionId: \\d+}")
    public void deleteNotificacion(@PathParam("notificacionId") Long notificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "NotificacionResource deleteNotificacion: input: {0}", notificacionId);
        if (logic.findNotificacion(notificacionId) == null) {
            throw new WebApplicationException("El recurso /notificacion/" + notificacionId + " no existe.", 404);
        }
        logic.deleteNotificacion(logic.findNotificacion(notificacionId));
        LOGGER.info("NotificacionResource deleteNotificacion: output: void");
    }
}
