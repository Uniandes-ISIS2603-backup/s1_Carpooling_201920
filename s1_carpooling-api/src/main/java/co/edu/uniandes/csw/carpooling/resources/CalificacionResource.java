/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Juan David Alarcón
 */
@Path("calificaciones")
@Produces("application/JSON")
@Consumes("application/JSON")
public class CalificacionResource {
 
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    @Inject CalificacionLogic calificacionLogic;
    
       
    @POST 
    public CalificacionDTO createCalificacionForViajero(@PathParam("viajerosId")Long viajerosId,CalificacionDTO calificacion) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Calificacion Resource create Calificacion: input: (0)", calificacion);
        CalificacionDTO nuevaCalificacion= new CalificacionDTO(calificacionLogic.createCalificacionForViajero(viajerosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "Calificacion Resource create Calificacion: output: (0)", nuevaCalificacion);
        return nuevaCalificacion;
    }
    
    @POST 
    public CalificacionDTO createCalificacionForConductor(@PathParam("conductoresId")Long conductoresId,CalificacionDTO calificacion) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Calificacion Resource create Calificacion: input: (0)", calificacion);
        CalificacionDTO nuevaCalificacion= new CalificacionDTO(calificacionLogic.createCalificacionForConductor(conductoresId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "Calificacion Resource create Calificacion: output: (0)", nuevaCalificacion);
        return nuevaCalificacion;
    }
    
    
    
    @GET
    public List<CalificacionDTO> getCalificacionesByViajero(@PathParam("viajerosId") long viajerosId)
    {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacions: input: {0}", viajerosId);
        List<CalificacionDTO> listaDTOs = listEntity2DTO(calificacionLogic.getCalificacionesByViajero(viajerosId));
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacions: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    public List<CalificacionDTO> getCalificacionesByConductor(@PathParam("conductoresId") long conductoresId)
    {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacions: input: {0}", conductoresId);
        List<CalificacionDTO> listaDTOs = listEntity2DTO(calificacionLogic.getCalificacionesByConductor(conductoresId));
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacions: output: {0}", listaDTOs);
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
    
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionesId);
        CalificacionEntity entity = calificacionLogic.getCalificacionByConductor(conductoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacionByViajero(@PathParam("viajerosId") Long viajerosId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: viajerosId: {0} , calificacionesId: {1} , calificacion:{2}", new Object[]{viajerosId, calificacionesId, calificacion});
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = calificacionLogic.getCalificacionByViajero(viajerosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajero/" + viajerosId + "/calificaciones/" + calificacionesId + " no existe.", 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacionByViajero(viajerosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output:{0}", calificacionDTO);
        return calificacionDTO;

    }
    
     @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: conductoresId: {0} , calificacionesId: {1} , calificacion:{2}", new Object[]{conductoresId, calificacionesId, calificacion});
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = calificacionLogic.getCalificacionByConductor(conductoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductor/" + conductoresId + "/calificaciones/" + calificacionesId + " no existe.", 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacionByConductor(conductoresId, calificacion.toEntity()));
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
    
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacionByConductor(@PathParam("conductoresId") Long conductoresId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = calificacionLogic.getCalificacionByConductor(conductoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        calificacionLogic.deleteCalificacionByConductor(conductoresId, calificacionesId);
    }
    
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<CalificacionDTO>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }

}   
