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
 * Clase que implementa el recurso Trayecto.
 * 
 * @author Juan David Serrano
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrayectoResource {
    
    private static final Logger LOGGER = Logger.getLogger(TrayectoResource.class.getName());
    
    @Inject
    private TrayectoLogic logic;
    
    @POST
    public TrayectoDTO createTrayecto(@PathParam("viajesId") Long viajesId, TrayectoDTO trayecto) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "TrayectoResource createReview: input: {0}", trayecto);
        TrayectoEntity trayectoEntity =trayecto.toEntity();
        trayectoEntity = logic.createTrayecto(viajesId, trayectoEntity);
        TrayectoDTO nuevoTrayecto = new TrayectoDTO(trayectoEntity);
        LOGGER.log(Level.INFO, "TrayectoResource createTrayecto: output: {0}", nuevoTrayecto);
        return nuevoTrayecto;
    }
    
    /**
     * Busca y devuelve todos los trayectos que tiene un viaje
     * @param viajesId Id del viaje que es dueño de los trayectos
     * @return JSONArray {@link TrayectoDTO} - Los trayectos encontrados en la
     * aplicación del viaje. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<TrayectoDTO> getTrayectos(@PathParam("viajesId") Long viajesId) {
        LOGGER.log(Level.INFO, "TrayectoResource getTrayectos: input: {0}", viajesId);
        List<TrayectoDTO> listaDTOs = listEntity2DTO(logic.getTrayectos(viajesId));
        LOGGER.log(Level.INFO, "TrayectoResource getTrayectos: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    /**
     * Busca el trayecto con el id asociado recibido en la URL de un viaje en especifico.
     * 
     * @param viajesId Identificador del viaje dueño del trayecto. Este debe ser una cadena de dígitos.
     * @param trayectosId Identificador del trayecto que se esta buscando. Este debe ser una cadena de digitos
     * @return JSON {@link TrayectoDTO} - El trayectos buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el trayecto.
     */
    @GET
    @Path("{trayectosId: \\d+}")
    public TrayectoDTO getTrayecto(@PathParam("viajesId") Long viajesId, @PathParam("trayectosId") Long trayectosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TrayectoResource getTrayecto: input: {0}", trayectosId);
        TrayectoEntity entity = logic.getTrayecto(trayectosId, viajesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + "/trayectos/" + trayectosId + " no existe.", 404);
        }
        TrayectoDTO trayectoDTO = new TrayectoDTO(entity);
        LOGGER.log(Level.INFO, "TrayectoResource getTrayecto: output: {0}", trayectoDTO);
        return trayectoDTO;
    }
    
    
    /**
     * Actualiza el trayecto con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param viajesId Identificador del viaje dueño del trayecto
     * @param trayectosId Identificador de trayecto que se desea actualizar
     * @param trayecto {@link TrayectoDTO} El trayecto que se desea guardar.
     * @return JSON {@link TrayectoDTO} - El trayecto guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el trayecto a
     * actualizar.
     * @throws BusinessLogicException Cuando el trayecto y el id pasado no coinciden
     */
    @PUT
    @Path("{trayectosId: \\d+}")
    public TrayectoDTO updateTrayecto(@PathParam("viajesId") Long viajesId, @PathParam("trayectosId") Long trayectosId, TrayectoDTO trayecto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TrayectoResource updateTrayecto: input: viajeId: {0} , trayectosId: {1} , trayecto:{2}", new Object[]{viajesId, trayectosId, trayecto});
        if (!trayectosId.equals(trayecto.getId())) {
            throw new BusinessLogicException("Los ids del Trayecto no coinciden.");
        }
        TrayectoEntity entity = logic.getTrayecto(trayectosId, viajesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + "/trayectos/" + trayectosId + " no existe.", 404);
        }
        TrayectoDTO trayectoDTO = new TrayectoDTO(logic.updateTrayecto(trayecto.toEntity(), viajesId));
        LOGGER.log(Level.INFO, "TrayectoResource updateTrayecto: output:{0}", trayectoDTO);
        return trayectoDTO;

    }
    
    /**
     * Borra el trayecto con el id asociado recibido en la URL.
     *
     * @param viajesId Identificador del viajes dueño del trayecto a borrar. Este debe
     * ser una cadena de dígitos.
     * @param trayectosId Id del trayecto que se desea borrar
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el trayecto a borrar.
     */
    @DELETE
    @Path("{trayectosId: \\d+}")
    public void deleteTrayecto(@PathParam("viajesId") Long viajesId, @PathParam("trayectosId") Long trayectosId) throws BusinessLogicException {
        TrayectoEntity entity = logic.getTrayecto(trayectosId, viajesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + "/trayectos/" + trayectosId + " no existe.", 404);
        }
        logic.deleteTrayecto(trayectosId, viajesId);
    }
    
    /**
     * Convierte una lista de TrayectoEntity a una lista de TrayectoDTO.
     *
     * @param entityList Lista de TrayectoEntity a convertir.
     * @return Lista de TrayectoDTO convertida.
     */
    private List<TrayectoDTO> listEntity2DTO(List<TrayectoEntity> entityList) {
        List<TrayectoDTO> list = new ArrayList<>();
        for (TrayectoEntity entity : entityList) {
            list.add(new TrayectoDTO(entity));
        }
        return list;
    }
    
}
