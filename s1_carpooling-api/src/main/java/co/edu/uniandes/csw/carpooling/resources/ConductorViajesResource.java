/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ViajeDTO;
import co.edu.uniandes.csw.carpooling.dtos.ViajeDetailDTO;
import co.edu.uniandes.csw.carpooling.ejb.ConductorLogic;
import co.edu.uniandes.csw.carpooling.ejb.VehiculoLogic;
import co.edu.uniandes.csw.carpooling.ejb.ViajeLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
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
 * @author JuanDavidSerrano
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConductorViajesResource {
    
    private static final Logger LOGGER = Logger.getLogger(ConductorViajesResource.class.getName());
   
    @Inject
    private ViajeLogic viajeLogic;
    
    @Inject
    private ConductorLogic conductorLogic; 
    
    @Inject 
    private VehiculoLogic vehiculoLogic;
    
    @POST
    @Path("{vehiculosId: \\d+}")
    public ViajeDTO createViaje(@PathParam("conductoresId") Long conductoresId, @PathParam("vehiculosId") Long vehiculosId, ViajeDTO viaje) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ConductorViajesResource createViaje: input: {0} con id de conductor: {1} y id de vehiculo: {2}", new Object[]{viaje, conductoresId, vehiculosId});
        ViajeEntity viajeEntity =viaje.toEntity();
        ConductorEntity conductorEntity = conductorLogic.getConductor(conductoresId);
        if(conductorEntity == null){
            throw new WebApplicationException("El recurso /conductorres/" + conductoresId + " no existe.", 404);
        }
        VehiculoEntity vehiculoEntity = vehiculoLogic.getVehiculo(conductoresId, vehiculosId);
        if(vehiculoEntity == null){
            throw new WebApplicationException("El recurso /vehiculos/" + vehiculosId + " no existe.", 404);
        }
        viajeEntity.setConductor(conductorEntity);
        viajeEntity.setVehiculo(vehiculoEntity);
        viajeEntity = viajeLogic.createViaje(viajeEntity);
        ViajeDetailDTO resultado = new ViajeDetailDTO(viajeEntity);
        LOGGER.log(Level.INFO, "ConductorViajesResource createViaje: output: {0}", resultado);
        return resultado;
    }
    
    @GET
    public List<ViajeDetailDTO> getViajes(@PathParam("conductoresId") Long conductoresId){
        LOGGER.info("ConductorViajesResource getViajes: input: void");
        List<ViajeDetailDTO> viajes = listViajesEntityToDTO(viajeLogic.getViajes(conductoresId));
        LOGGER.log(Level.INFO, "ConductorViajesResource getViajes: output: {0}", viajes);
        return viajes;
    }
    
    @GET
    @Path("{viajesId: \\d+}")
    public ViajeDetailDTO getViaje(@PathParam("conductoresId") Long conductoresId, @PathParam("viajesId") Long viajesId){
        LOGGER.log(Level.INFO, "ConductorViajesResource getViaje: input: {0}", viajesId);
        ViajeEntity viajeEntity = viajeLogic.getViaje(conductoresId ,viajesId);
        if (viajeEntity == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + " no existe.", 404);
        }
        ViajeDetailDTO detailDTO = new ViajeDetailDTO(viajeEntity);
        LOGGER.log(Level.INFO, "ViajeResource getViaje: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @PUT
    @Path("{viajesId: \\d+}")
    public ViajeDetailDTO updateViaje(@PathParam("conductoresId") Long conductoresId,
            @PathParam("viajesId") Long viajesId, ViajeDetailDTO viaje) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ConductorViajesResource updateViaje: input: viajesId: {0} , author: {1}", new Object[]{viajesId, viaje});
        viaje.setId(viajesId);
        if (viajeLogic.getViaje(viajesId) == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + " no existe.", 404);
        }
        ViajeEntity entity = viajeLogic.getViaje(conductoresId, viajesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/viajes/" + viajesId + " no existe.", 404);
        }
        VehiculoEntity entity2 = vehiculoLogic.getVehiculo(conductoresId, viaje.getVehiculo().getId());
        if(entity2 == null){
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/vehiculos/" + viaje.getVehiculo().getId() + " no existe.", 404);
        }
        
        ViajeEntity viajeEntity = viaje.toEntity();
        viajeEntity.setConductor(conductorLogic.getConductor(conductoresId));
        
        ViajeDetailDTO detailDTO = new ViajeDetailDTO(viajeLogic.updateViaje(viajeEntity));
        LOGGER.log(Level.INFO, "ConductorViajesResource updateViaje: output: {0}", detailDTO);
        return detailDTO;
    }
    
    
    @DELETE
    @Path("{viajesId: \\d+}")
    public void deleteViaje(@PathParam("conductoresId") Long conductoresId, @PathParam("viajesId") Long viajesId) throws BusinessLogicException {
        ViajeEntity entity = viajeLogic.getViaje(conductoresId, viajesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/viajes/" + viajesId + " no existe.", 404);
        }
        viajeLogic.deleteViaje(viajesId);
    }
     
  
     private List<ViajeDetailDTO> listViajesEntityToDTO(List<ViajeEntity> viajes){
        List<ViajeDetailDTO> viajesDTO = new ArrayList<>();
        for(ViajeEntity viaje: viajes){
            viajesDTO.add(new ViajeDetailDTO(viaje));
        }
        return viajesDTO;
    }
     
    
}
