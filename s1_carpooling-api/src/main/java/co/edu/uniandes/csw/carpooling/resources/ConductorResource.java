/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ConductorDTO;
import co.edu.uniandes.csw.carpooling.dtos.ConductorDetailDTO;
import co.edu.uniandes.csw.carpooling.ejb.ConductorLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Nicolas Fajardo
 */

@Path("/conductores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ConductorResource {
    
    /**
     * el logger
     */
    private static final Logger LOGGER = Logger.getLogger(ConductorResource.class.getName());
    
    /**
     * la logica
     */
    @Inject
    private ConductorLogic logica;
    
    /**
     * metodo crear conductor
     * @param conductor el conductor a crear
     * @return el conductor creado
     * @throws BusinessLogicException si el conductor a crear rompe con alguna de las reglas de negocio
     */
    @POST
    public ConductorDTO createConductor(ConductorDTO conductor)throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ConductorResource createConductor: input: {0}", conductor);
        ConductorEntity conductorEntity = conductor.toEntity();
        ConductorEntity nuevaEntity = logica.createConductor(conductorEntity);
        ConductorDTO respDTO = new ConductorDTO(nuevaEntity);
        LOGGER.log(Level.INFO, "ConductorResource createConductor: output: {0}", respDTO);
        return respDTO;
    }
    
    /**
     * metodo para obtener todos los conductores
     * @return todos los conductores de la base de datos
     */
    @GET
    public List<ConductorDetailDTO> getConductores() {
        LOGGER.info("CondcutorResource getConductores: input: void");
        List<ConductorDetailDTO> listaConductores = listEntity2DetailDTO(logica.getConductores());
        LOGGER.log(Level.INFO, "ConductorResource getConductores: output: {0}", listaConductores);
        return listaConductores;
    }
    
    /**
     * metodo para retornar a un conductor dado su id
     * @param conductoresId el id del conductor a retornar
     * @return el conductor con id que entra por parametro
     * @throws WebApplicationException si ocurre algun error
     */
    @GET
    @Path("{conductoresId: \\d+}")
    public ConductorDetailDTO getConductor(@PathParam("conductoresId") Long conductoresId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ConductorResource getConductor: input: {0}", conductoresId);
        ConductorEntity conductorEntity = logica.getConductor(conductoresId);
        if (conductorEntity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + " no existe.", 404);
        }
        ConductorDetailDTO detailDTO = new ConductorDetailDTO(conductorEntity);
        LOGGER.log(Level.INFO, "ConductorResource getConductor: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Metodo para alterar un conductor
     * @param conductoresId el id del conductor a actualizar
     * @param conductor el conductor actualizado
     * @return el conductor actualizado
     * @throws WebApplicationException si ocurre algun error
     * @throws BusinessLogicException si el conductor actualizado rompe con alguna de las reglas del negocio
     */
    @PUT
    @Path("{conductoresId: \\d+}")
    public ConductorDetailDTO updateConductor(@PathParam("conductoresId") Long conductoresId, ConductorDetailDTO conductor) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "ConductorResource updateConductor: input: id:{0} , Conductor: {1}", new Object[]{conductoresId, conductor});
        conductor.setId(conductoresId);
        if (logica.getConductor(conductoresId) == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + " no existe.", 404);
        }
        ConductorDetailDTO detailDTO = new ConductorDetailDTO(logica.actualizarConductor(conductor.toEntity()));
        LOGGER.log(Level.INFO, "ConductorResource updateConductor: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Metodo para eliminar un conductor
     * @param conductoresId el id del condutor a eliminar
     * @throws BusinessLogicException si al eliminar el conductor se rompe alguna de las reglas del negocio
     */
    @DELETE
    @Path("{conductoresId: \\d+}")
    public void deleteConductor(@PathParam("conductoresId") Long conductoresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ConductorResource deleteConductor: input: {0}", conductoresId);
        if (logica.getConductor(conductoresId) == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + " no existe.", 404);
        }
        logica.deleteConductor(conductoresId);
        LOGGER.info("ConductorResource deleteConductor: output: void");
    }
    
    /**
     * Retorna el elemento conductorViajes para el conductor con id que entra por paramtro
     * @param conductoresId el id del conductor a retorna el elemento conductorViajes
     * @return el elemento conductorViajes del conductor con id que entra por parametro
     */
    @Path("{conductoresId: \\d+}/viajes")
    public Class<ConductorViajesResource> getConductorViajesResource(@PathParam("conductoresId") Long conductoresId) {
        if (logica.getConductor(conductoresId) == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "Se retorna la clase ConductorViajesResource");
        return ConductorViajesResource.class;
    }
    
    /**
     * obtiene los vehiculos del conductor con id que entra por parametro
     * @param conductoresId el id del conductor
     * @return retorna los vehìculos del conductor con id igual al ingresado por parametro
     */
    @Path("{conductoresId: \\d+}/vehiculos")
    public Class<VehiculoResource> getVehiculosResource(@PathParam("conductoresId") Long conductoresId) {
        if (logica.getConductor(conductoresId) == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + " no existe.", 404);
        }
        return VehiculoResource.class;
    }
    
    /**
     * Obtiene las calificaciones del conductor
     * @param conductoresId el id del conductor
     * @return las calificaciones del conductor cuyo id entra por paramtero
     */
    @Path("{conductoresId: \\d+}/calificaciones")
    public Class<ConductorCalificacionResource> getCalificacionesResource(@PathParam("conductoresId") Long conductoresId) {
        if (logica.getConductor(conductoresId) == null) {
            throw new WebApplicationException("El recurso /conductor/" + conductoresId + " no existe.", 404);
        }
        return ConductorCalificacionResource.class;
    }
    
    /**
     * retorna las notificaciones del conductor
     * @param conductoresId el id del conductor
     * @return las notificaciones del conductor cuyo id entra por parametro
     */
    @Path("{conductoresId: \\d+}/notificaciones")
    public Class<ConductorNotificacionResource> getNotificacionesResource(@PathParam("notificacionesId") Long conductoresId) {
        if (logica.getConductor(conductoresId) == null) {
            throw new WebApplicationException("El recurso /conductor/" + conductoresId + " no existe.", 404);
        }
        return ConductorNotificacionResource.class;
    }
    
    /**
     * retorna los viajes recurrentes del conductor
     * @param conductoresId el id del conductor
     * @return los viajes recurrentes del conductor cuyo id entra por parametro
     */
    @Path("{conductoresId: \\d+}/viajesRecurrentes")
    public Class<ViajeRecurrenteResource> getViajeRecurrenteResource(@PathParam("conductoresId") Long conductoresId) {
        if (logica.getConductor(conductoresId) == null) {
            throw new WebApplicationException("El recurso /conductor /" + conductoresId + " no existe.", 404);
        }
        return ViajeRecurrenteResource.class;
    }
    
    /**
     * retorna una lista de dtos a base de una lista de entities
     * @param entities la lista de entities
     * @return la lista de dtos creada a base de los entities que entran por parametro
     */
    private List<ConductorDetailDTO> listEntity2DetailDTO(List<ConductorEntity> entities){
        List<ConductorDetailDTO> list = new ArrayList<> ();
        for(ConductorEntity entity: entities){
            list.add(new ConductorDetailDTO(entity));
        }
        return list;
    }
}
