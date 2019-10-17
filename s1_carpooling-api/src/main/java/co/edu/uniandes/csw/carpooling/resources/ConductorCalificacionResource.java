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
@Path("/calificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConductorCalificacionResource {
 
    private static final Logger LOGGER = Logger.getLogger(ConductorCalificacionResource.class.getName());
    @Inject CalificacionLogic calificacionLogic;
    
    
    
        
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
        
    
    
    @POST
    public CalificacionDTO createCalificacionByConductor(@PathParam("conductoresId") long conductoresId, CalificacionDTO calificacion) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion);
         CalificacionDTO nuevoCalificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacionForConductor(conductoresId, calificacion.toEntity()));
         LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevoCalificacionDTO);
        return nuevoCalificacionDTO;
    }
    
    @GET
    public List<CalificacionDTO> getCalificacionesByConductor(@PathParam("conductoresId") long conductoresId)
    {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: input: {0}", conductoresId);
        List<CalificacionDTO> listaDTOs = listEntity2DTO(calificacionLogic.getCalificacionesByConductor(conductoresId));
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionesId);
        CalificacionEntity entity = calificacionLogic.getCalificacionByConductor(conductoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /esto es en calificacion/" + conductoresId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    /*            
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: conductoresId: {0} , calificacionesId: {1} , calificacion:{2}", new Object[]{conductoresId, calificacionesId, calificacion});
        if (!calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = calificacionLogic.getCalificacionByConductor(conductoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/calificaciones/" + calificacionesId + " no existe.", 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacionByConductor(conductoresId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output:{0}", calificacionDTO);
        return calificacionDTO;
    }
    */
    /**
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = calificacionLogic.getCalificacionByConductor(conductoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        calificacionLogic.deleteCalificacionByConductor(conductoresId, calificacionesId);
    }
  */
}   
