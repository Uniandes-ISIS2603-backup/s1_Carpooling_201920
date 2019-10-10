package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ViajeDTO;
import co.edu.uniandes.csw.carpooling.dtos.ViajeRecurrenteDetailDTO;
import co.edu.uniandes.csw.carpooling.ejb.ViajeRecurrenteLogic;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ViajeRecurrenteResource {
 
    private static final Logger LOGGER = Logger.getLogger(ViajeRecurrenteResource.class.getName());
    @Inject ViajeRecurrenteLogic viajeRecurrenteLogic;
    
    @POST
    public ViajeRecurrenteDetailDTO createViajeRecurrente(@PathParam("conductoresId") long conductoresId, ViajeRecurrenteDetailDTO viajeRecurrente) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource createViajeRecurrenteDetail: input: {0}", viajeRecurrente);
         
         //El detail debe venir con un viaje.
        /** 
         * En esta parte se deberían generar automaticamente los viajes entre las dos fechas de inicio 
         * con la frecuencia semanalmente
         * Por implementar, por ahora no es completamente necesario
         * ViajeDTO viajeInicial = viajeRecurrente.getViajes().get(0);
         
         
         Date fechaInicial = viajeRecurrente.getFechInicio();
         Calendar c = Calendar.getInstance();
         c.setTime(fechaInicial);
         int diaInicial = c.get(Calendar.DAY_OF_WEEK);
         
         
         Date fechaFinal = viajeRecurrente.getFechaFin();
         c.setTime(fechaFinal);
         
         int diaFinal = c.get(Calendar.DAY_OF_WEEK);
         
        String frecuencia = viajeRecurrente.getFrecuencia();
        String[] frecuencias= frecuencia.split(",");
        Integer[] frecs = new Integer[frecuencias.length];
         for(int i = 0;  i < frecuencias.length ; i++)
        {
            frecs[i]  = Integer.parseInt(frecuencias[i]);
        }
         
        long weeks = ChronoUnit.WEEKS.between(fechaInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), fechaFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        
        //Semana inicial
        for(int i = 0; i < frecs.length; i++)
        {
            if(frecs[i]> diaInicial)
            {
                ViajeDTO temp = new ViajeDTO(viajeInicial.toEntity());
                c.setTime();            
                c.add(Calendar.DAY_OF_YEAR, noOfDays);
                
                ViajeDTO viaje = new ViajeDTO()
            }
                viajeRecurrente.getViajes().add(viajeInicial)
                
                
        }
        
        for(int i = 0; i < weeks; i++)
        {
            
        }
        */
         
        ViajeRecurrenteDetailDTO nuevoViajeRecurrenteDetailDTO = new ViajeRecurrenteDetailDTO(viajeRecurrenteLogic.createViajeRecurrente(conductoresId, viajeRecurrente.toEntity()));

         
         LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource createViajeRecurrenteDetail: output: {0}", nuevoViajeRecurrenteDetailDTO);
        return nuevoViajeRecurrenteDetailDTO;
    }
    
    @GET
    public List<ViajeRecurrenteDetailDTO> getViajesRecurrentes(@PathParam("conductoresId") long conductoresId)
    {
        LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource getViajeRecurrenteDetails: input: {0}", conductoresId);
        List<ViajeRecurrenteDetailDTO> listaDTOs = listEntity2DTO(viajeRecurrenteLogic.getViajesRecurrentes(conductoresId));
        LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource getViajeRecurrenteDetails: output: {0}", listaDTOs);
        return listaDTOs;
    }
    @GET
    @Path("{viajesRecurrentesId: \\d+}")
    public ViajeRecurrenteDetailDTO getViajeRecurrente(@PathParam("conductoresId") Long conductoresId, @PathParam("viajesRecurrentesId") Long viajesRecurrentesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource getViajeRecurrenteDetail: input: {0}", viajesRecurrentesId);
        ViajeRecurrenteEntity entity = viajeRecurrenteLogic.getViajeRecurrente(conductoresId, viajesRecurrentesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/viajesRecurrentes/" + viajesRecurrentesId + " no existe.", 404);
        }
        ViajeRecurrenteDetailDTO viajesRecurrenteDTO = new ViajeRecurrenteDetailDTO(entity);
        LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource getViajeRecurrenteDetail: output: {0}", viajesRecurrenteDTO);
        return viajesRecurrenteDTO;
    }
        private List<ViajeRecurrenteDetailDTO> listEntity2DTO(List<ViajeRecurrenteEntity> entityList) {
        List<ViajeRecurrenteDetailDTO> list = new ArrayList<ViajeRecurrenteDetailDTO>();
        for (ViajeRecurrenteEntity entity : entityList) {
            list.add(new ViajeRecurrenteDetailDTO(entity));
        }
        return list;
    }
        
    @PUT
    @Path("{viajesRecurrentesId: \\d+}")
    public ViajeRecurrenteDetailDTO updateViajeRecurrente(@PathParam("conductoresId") Long conductoresId, @PathParam("viajesRecurrentesId") Long viajesRecurrentesId, ViajeRecurrenteDetailDTO viajesRecurrente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource updateViajeRecurrenteDetail: input: conductoresId: {0} , viajesRecurrentesId: {1} , viajesRecurrente:{2}", new Object[]{conductoresId, viajesRecurrentesId, viajesRecurrente});
        if (!viajesRecurrentesId.equals(viajesRecurrente.getId())) {
            throw new BusinessLogicException("Los ids del ViajeRecurrenteDetail no coinciden.");
        }
        ViajeRecurrenteEntity entity = viajeRecurrenteLogic.getViajeRecurrente(conductoresId, viajesRecurrentesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/viajesRecurrentes/" + viajesRecurrentesId + " no existe.", 404);

        }
        ViajeRecurrenteDetailDTO viajesRecurrenteDTO = new ViajeRecurrenteDetailDTO(viajeRecurrenteLogic.updateViajeRecurrente(conductoresId, viajesRecurrente.toEntity()));
        LOGGER.log(Level.INFO, "ViajeRecurrenteDetailResource updateViajeRecurrenteDetail: output:{0}", viajesRecurrenteDTO);
        return viajesRecurrenteDTO;
    }
    
    @DELETE
    @Path("{viajesRecurrentesId: \\d+}")
    public void deleteViajeRecurrente(@PathParam("conductoresId") Long conductoresId, @PathParam("viajesRecurrentesId") Long viajesRecurrentesId) throws BusinessLogicException {
        ViajeRecurrenteEntity entity = viajeRecurrenteLogic.getViajeRecurrente(conductoresId, viajesRecurrentesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /conductores/" + conductoresId + "/viajesRecurrentes/" + viajesRecurrentesId + " no existe.", 404);
        }
        viajeRecurrenteLogic.deleteViajeRecurrente(conductoresId, viajesRecurrentesId);
    }
}   
