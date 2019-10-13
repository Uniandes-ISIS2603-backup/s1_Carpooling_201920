/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ReservaDTO;
import co.edu.uniandes.csw.carpooling.ejb.ReservaLogic;
import co.edu.uniandes.csw.carpooling.ejb.ViajeLogic;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author le.perezl
 */
@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ReservaResource {
    
   private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(ReservaResource.class.getName());
    
   @Inject
   private ReservaLogic logic;
    
   @POST
   public ReservaDTO createReserva(ReservaDTO reserva) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ReservaResource createReserva: input: {0}", reserva);
        ReservaEntity reservaEntity =reserva.toEntity();
        reservaEntity = logic.createReserva(reservaEntity);
        return reserva;
   }
   
//    @GET
//    public List<ReservaDTO> getReservas(){
//        LOGGER.info("ReservaResource getReservas: input: void");
//        List<ReservaDTO> reserva = listViajesEntityToDTO(logic.findReservas());
//        LOGGER.log(Level.INFO, "ReservaResource getReservas: output: {0}", reserva);
//        return reserva;
//    } 
   
    @GET
    @Path("{reservasId: \\d+}")
    public ReservaDTO getReserva(@PathParam("reservasId") Long reservasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ReservaResource getReserva: input: {0}", reservasId);
        ReservaEntity reservaEntity = logic.findReserva(reservasId);
        if (reservaEntity == null) {
            throw new WebApplicationException("El recurso /reserva/" + reservasId + " no existe.", 404);
        }
        ReservaDTO detailDTO = new ReservaDTO(reservaEntity);
        LOGGER.log(Level.INFO, "ReservaResource getReserva: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @PUT
    @Path("{reservasId: \\d+}")
    public ReservaDTO updateReserva(@PathParam("reservasId") Long reservasId, ReservaDTO reserva) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ReservaResource updatReserva: input: reservasId: {0} , author: {1}", new Object[]{reservasId, reserva});
        reserva.setId(reservasId);
        if (logic.findReserva(reservasId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDTO detailDTO = new ReservaDTO(logic.updateReserva(reservasId, reserva.toEntity()));
        LOGGER.log(Level.INFO, "ReservaResource updateReserva: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{reservasId: \\d+}")
    public void deleteReserva(@PathParam("reservasId") Long reservasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ReservaResource deleteReserva: input: {0}", reservasId);
        if (logic.findReserva(reservasId) == null) {
            throw new WebApplicationException("El recurso /reservas/" + reservasId + " no existe.", 404);
        }
        logic.deleteReserva(logic.findReserva(reservasId));
        LOGGER.info("ReservaResource deleteReserva: output: void");
    }
    

       /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos BookEntity a una lista de
     * objetos BookDetailDTO (json)
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de libros en forma DTO (json)
     */
//    private List<ReservaDTO> listEntity2DetailDTO(List<ReservaDTO> entityList) {
//        List<ReservaDTO> list = new ArrayList<ReservaDTO>();
//        for (ReservaEntity reserva : entityList) {
//            list.add(new ReservaDTO(reserva));
//        }
//        return list;
//        
//    }   
    
}