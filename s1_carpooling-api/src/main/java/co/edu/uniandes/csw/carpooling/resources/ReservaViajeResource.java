/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ReservaDTO;
import co.edu.uniandes.csw.carpooling.ejb.ReservaLogic;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author le.perezl
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaViajeResource {
    
        
    private static final Logger LOGGER = Logger.getLogger(TrayectoResource.class.getName());
    
    @Inject
    private ReservaLogic logic;
    
    @POST
    public ReservaDTO createReserva(@PathParam("viajesId") Long viajesId, ReservaDTO reserva) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "TrayectoResource createReview: input: {0}", reserva);
        ReservaEntity reservaEntity =reserva.toEntity();
        reservaEntity = logic.createReserva(viajesId, reservaEntity);
        ReservaDTO nuevaReserva = new ReservaDTO(reservaEntity);
        LOGGER.log(Level.INFO, "TrayectoResource createTrayecto: output: {0}", nuevaReserva);
        return nuevaReserva;
    }
    @GET
    public List<ReservaDTO> getReservas(@PathParam("viajesId") Long viajesId) {
        LOGGER.log(Level.INFO, "TrayectoResource getTrayectos: input: {0}", viajesId);
        List<ReservaDTO> listaDTOs = listEntity2DTO(logic.getReservasByViaje(viajesId));
        LOGGER.log(Level.INFO, "TrayectoResource getTrayectos: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{reservasId: \\d+}")
    public ReservaDTO getReserva(@PathParam("viajesId") Long viajesId, @PathParam("reservasId") Long reservasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ReservaResource getReserva: input: {0}", reservasId);
        ReservaEntity entity = logic.findReservaByViaje(reservasId, viajesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viajes/" + viajesId + "/reservas/" + reservasId + " no existe.", 404);
        }
        ReservaDTO reservaDTO = new ReservaDTO(entity);
        LOGGER.log(Level.INFO, "ReservaViajeResource getReserva: output: {0}", reservaDTO);
        return reservaDTO;
    }
    
    private List<ReservaDTO> listEntity2DTO(List<ReservaEntity> entityList) {
        List<ReservaDTO> list = new ArrayList<>();
        for (ReservaEntity entity : entityList) {
            list.add(new ReservaDTO(entity));
        }
        return list;
    }
}