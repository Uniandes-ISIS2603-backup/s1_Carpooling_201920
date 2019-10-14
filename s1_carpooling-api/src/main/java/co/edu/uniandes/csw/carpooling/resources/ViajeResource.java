/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ViajeDTO;
import co.edu.uniandes.csw.carpooling.dtos.ViajeDetailDTO;
import co.edu.uniandes.csw.carpooling.ejb.ViajeLogic;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
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
 * @author Estudiante
 */
@Path("/viajes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ViajeResource {
    
    private static final Logger LOGGER = Logger.getLogger(ViajeResource.class.getName());
    
    @Inject
    private ViajeLogic logic;
    
    
    @GET
    public List<ViajeDetailDTO> getViajes(){
        LOGGER.info("ViajeResource getViajes: input: void");
        List<ViajeDetailDTO> viajes = listViajesEntityToDTO(logic.getViajes());
        LOGGER.log(Level.INFO, "ViajeResource getViajes: output: {0}", viajes);
        return viajes;
    } 
    
    
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
    
    
    @PUT
    @Path("{viajesId: \\d+}")
    public ViajeDetailDTO updateViaje(@PathParam("viajesId") Long viajesId, ViajeDetailDTO viaje) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ViajeResource updateViaje: input: viajesId: {0} , author: {1}", new Object[]{viajesId, viaje});
        viaje.setId(viajesId);
        if (logic.getViaje(viajesId) == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + " no existe.", 404);
        }
        ViajeDetailDTO detailDTO = new ViajeDetailDTO(logic.updateViaje(viajesId, viaje.toEntity()));
        LOGGER.log(Level.INFO, "ViajeResource updateViaje: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @Path("{viajesId: \\d+}/trayectos")
    public Class<TrayectoResource> getTrayectoResource(@PathParam("viajesId") Long viajesId) {
        if (logic.getViaje(viajesId) == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + "/trayectos no existe.", 404);
        }
        return TrayectoResource.class;
    }
    
    private List<ViajeDetailDTO> listViajesEntityToDTO(List<ViajeEntity> viajes){
        List<ViajeDetailDTO> viajesDTO = new ArrayList<ViajeDetailDTO>();
        for(ViajeEntity viaje: viajes){
            viajesDTO.add(new ViajeDetailDTO(viaje));
        }
        return viajesDTO;
    }
    
    
    
    
    
}
