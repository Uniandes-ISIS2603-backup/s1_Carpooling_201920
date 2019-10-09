package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.VehiculoDTO;
import co.edu.uniandes.csw.carpooling.ejb.VehiculoLogic;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
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
public class VehiculoResource {
 
    private static final Logger LOGGER = Logger.getLogger(VehiculoResource.class.getName());
    @Inject VehiculoLogic vehiculoLogic;
    
    @POST
    public VehiculoDTO createVehiculo(@PathParam("conductoresId") long conductoresId, VehiculoDTO vehiculo) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "VehiculoResource createVehiculo: input: {0}", vehiculo);
         VehiculoDTO nuevoVehiculoDTO = new VehiculoDTO(vehiculoLogic.createVehiculo(conductoresId, vehiculo.toEntity()));
         LOGGER.log(Level.INFO, "VehiculoResource createVehiculo: output: {0}", nuevoVehiculoDTO);
        return nuevoVehiculoDTO;
    }
    
    @GET
    public List<VehiculoDTO> getVehiculos(@PathParam("conductoresId") long conductoresId)
    {
        LOGGER.log(Level.INFO, "VehiculoResource getVehiculos: input: {0}", conductoresId);
        List<VehiculoDTO> listaDTOs = listEntity2DTO(vehiculoLogic.getVehiculos(conductoresId));
        LOGGER.log(Level.INFO, "VehiculoResource getVehiculos: output: {0}", listaDTOs);
        return listaDTOs;
    }
    @GET
    @Path("{vehiculosId: \\d+}")
    public VehiculoDTO getVehiculo(@PathParam("conductoresId") Long conductoresId, @PathParam("vehiculosId") Long vehiculosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "VehiculoResource getVehiculo: input: {0}", vehiculosId);
        VehiculoEntity entity = vehiculoLogic.getVehiculo(conductoresId, vehiculosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/vehiculos/" + vehiculosId + " no existe.", 404);
        }
        VehiculoDTO vehiculoDTO = new VehiculoDTO(entity);
        LOGGER.log(Level.INFO, "VehiculoResource getVehiculo: output: {0}", vehiculoDTO);
        return vehiculoDTO;
    }
        private List<VehiculoDTO> listEntity2DTO(List<VehiculoEntity> entityList) {
        List<VehiculoDTO> list = new ArrayList<VehiculoDTO>();
        for (VehiculoEntity entity : entityList) {
            list.add(new VehiculoDTO(entity));
        }
        return list;
    }
        
    @PUT
    @Path("{vehiculosId: \\d+}")
    public VehiculoDTO updateVehiculo(@PathParam("conductoresId") Long conductoresId, @PathParam("vehiculosId") Long vehiculosId, VehiculoDTO vehiculo) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "VehiculoResource updateVehiculo: input: conductoresId: {0} , vehiculosId: {1} , vehiculo:{2}", new Object[]{conductoresId, vehiculosId, vehiculo});
        if (vehiculosId.equals(vehiculo.getId())) {
            throw new BusinessLogicException("Los ids del Vehiculo no coinciden.");
        }
        VehiculoEntity entity = vehiculoLogic.getVehiculo(conductoresId, vehiculosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/vehiculos/" + vehiculosId + " no existe.", 404);

        }
        VehiculoDTO vehiculoDTO = new VehiculoDTO(vehiculoLogic.updateVehiculo(conductoresId, vehiculo.toEntity()));
        LOGGER.log(Level.INFO, "VehiculoResource updateVehiculo: output:{0}", vehiculoDTO);
        return vehiculoDTO;
    }
    
    @DELETE
    @Path("{vehiculosId: \\d+}")
    public void deleteVehiculo(@PathParam("conductoresId") Long conductoresId, @PathParam("vehiculosId") Long vehiculosId) throws BusinessLogicException {
        VehiculoEntity entity = vehiculoLogic.getVehiculo(conductoresId, vehiculosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/vehiculos/" + vehiculosId + " no existe.", 404);
        }
        vehiculoLogic.deleteVehiculo(conductoresId, vehiculosId);
    }
}   
