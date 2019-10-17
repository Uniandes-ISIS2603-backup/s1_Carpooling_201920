package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.CalificacionDTO;
import co.edu.uniandes.csw.carpooling.ejb.CalificacionLogic;
import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Juan David Alarcón
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ViajeroCalificacionResource {
 
    private static final Logger LOGGER = Logger.getLogger(ViajeroCalificacionResource.class.getName());
    @Inject CalificacionLogic calificacionLogic;
    
    @POST
    public CalificacionDTO createCalificacionByViajero(@PathParam("viajerosId") long viajerosId, CalificacionDTO calificacion) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion);
         CalificacionDTO nuevoCalificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacionForViajero(viajerosId, calificacion.toEntity()));
         LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevoCalificacionDTO);
        return nuevoCalificacionDTO;
    }
    
    @GET
    public List<CalificacionDTO> getCalificacionesByViajero(@PathParam("viajerosId") long viajerosId)
    {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: input: {0}", viajerosId);
        List<CalificacionDTO> listaDTOs = listEntity2DTO(calificacionLogic.getCalificacionesByViajero(viajerosId));
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacionByViajero(@PathParam("viajerosId") Long viajerosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionesId);
        CalificacionEntity entity = calificacionLogic.getCalificacionByViajero(viajerosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajeros/" + viajerosId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
        private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
    
        /**
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacionByViajero(@PathParam("viajerosId") Long viajerosId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: viajerosId: {0} , calificacionesId: {1} , calificacion:{2}", new Object[]{viajerosId, calificacionesId, calificacion});
        if (!calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = calificacionLogic.getCalificacionByViajero(viajerosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajeros/" + viajerosId + "/calificaciones/" + calificacionesId + " no existe.", 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacionByViajero(viajerosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output:{0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacionByViajero(@PathParam("viajerosId") Long viajerosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = calificacionLogic.getCalificacionByViajero(viajerosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajeros/" + viajerosId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        calificacionLogic.deleteCalificacionByViajero(viajerosId, calificacionesId);
    }
  */  
    
}   
