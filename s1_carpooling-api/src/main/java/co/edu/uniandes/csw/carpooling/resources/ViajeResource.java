/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ViajeDetailDTO;
import co.edu.uniandes.csw.carpooling.ejb.ViajeLogic;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso Viaje.
 * 
 * @author juan David Serrano
 */
@Path("/viajes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ViajeResource {
    
    private static final Logger LOGGER = Logger.getLogger(ViajeResource.class.getName());
    
    @Inject
    private ViajeLogic logic;
    
    
    /**
     * Busca y devuelve todos los viajes que existen en la aplicacion.
     *
     * @return JSONArray {@link ViajeDetailDTO} - Los viajes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ViajeDetailDTO> getViajes(){
        LOGGER.info("ViajeResource getViajes: input: void");
        List<ViajeDetailDTO> viajes = listViajesEntityToDTO(logic.getViajes());
        LOGGER.log(Level.INFO, "ViajeResource getViajes: output: {0}", viajes);
        return viajes;
    } 
    
    
    /**
     * Busca el viaje con el id asociado recibido en la URL y lo devuelve.
     *
     * @param viajesId Identificador del viaje que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ViajeDetailDTO} - El viaje buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el viaje.
     */
    @GET
    @Path("{viajesId: \\d+}")
    public ViajeDetailDTO getViaje(@PathParam("viajesId") Long viajesId) {
        LOGGER.log(Level.INFO, "ViajeResource getViaje: input: {0}", viajesId);
        ViajeEntity viajeEntity = logic.getViaje(viajesId);
        if (viajeEntity == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + " no existe.", 404);
        }
        ViajeDetailDTO detailDTO = new ViajeDetailDTO(viajeEntity);
        LOGGER.log(Level.INFO, "ViajeResource getViaje: output: {0}", detailDTO);
        return detailDTO;
    }
    
    
     /**
     * Conexión con el servicio de trayectos para un viaje.
     * {@link TrayectoResource}
     *
     * Este método conecta la ruta de /viajes con las rutas de /trayectos que
     * dependen del viaje, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los trayectos.
     *
     * @param viajesId El ID del viaje con respecto al cual se accede al
     * servicio.
     * @return El servicio de trayectos para ese viaje en paricular.
     */
    @Path("{viajesId: \\d+}/trayectos")
    public Class<TrayectoResource> getTrayectoResource(@PathParam("viajesId") Long viajesId) {
        if (logic.getViaje(viajesId) == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + "/trayectos no existe.", 404);
        }
        return TrayectoResource.class;
    }
    
    /**
     * Convierte una lista de ViajeEntity a una lista de ViajeDetailDTO.
     *
     * @param viajes Lista de ViajeEntity a convertir.
     * @return Lista de ViajeDetailDTO convertida.
     */
    private List<ViajeDetailDTO> listViajesEntityToDTO(List<ViajeEntity> viajes){
        List<ViajeDetailDTO> viajesDTO = new ArrayList<>();
        for(ViajeEntity viaje: viajes){
            viajesDTO.add(new ViajeDetailDTO(viaje));
        }
        return viajesDTO;
    }
    
    
    
    
    
}
