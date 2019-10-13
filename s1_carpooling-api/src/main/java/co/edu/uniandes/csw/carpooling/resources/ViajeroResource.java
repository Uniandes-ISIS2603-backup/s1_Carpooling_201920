/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.ViajeroDetailDTO;
import co.edu.uniandes.csw.carpooling.dtos.ViajeroDTO;
import co.edu.uniandes.csw.carpooling.ejb.ViajeroLogic;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
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
 * @author Santiago Ballesteros
 */
@Path("/viajeros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ViajeroResource {

    private static final Logger LOGGER = Logger.getLogger(ViajeroResource.class.getName());

    @Inject
    private ViajeroLogic logica;

    @POST
    public ViajeroDTO createViajero(ViajeroDTO viajero) throws BusinessLogicException {

        ViajeroEntity viajeroEntity = viajero.toEntity();
        viajeroEntity = logica.createViajero(viajeroEntity);
        return new ViajeroDTO(viajeroEntity);

    }

    @GET
    @Path("{viajerosId: \\d+}")
    public ViajeroDetailDTO getViajero(@PathParam("viajerosId") Long viajerosId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ViajeroResource getViajero: input: {0}", viajerosId);
        // Me devuelve la entidad por su id
        ViajeroEntity viajeroEntity = logica.getViajero(viajerosId);

        // Si el objeto no existe significa que es nulo
        if (viajeroEntity == null) {
            throw new WebApplicationException("El recurso /viajeros/" + viajerosId + " no existe.", 404);
        }
        ViajeroDetailDTO detailDTO = new ViajeroDetailDTO(viajeroEntity);
        LOGGER.log(Level.INFO, "ViajeroResource getViajero: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos las viajeros que existen en la aplicacion.
     *
     * @return JSONArray {@link ViajeroDetailDTO} - Las viajeros
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ViajeroDetailDTO> getViajeros() {
        LOGGER.info("ViajeroResource getViajeros: input: void");
        List<ViajeroDetailDTO> listaViajeros = listEntity2DetailDTO(logica.getViajeros());
        LOGGER.log(Level.INFO, "ViajeroResource getViajeros: output: {0}", listaViajeros);
        return listaViajeros;
    }

    

    /**
     * Actualiza la viajero con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param viajerosId Identificador de la viajero que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param viajero {@link ViajeroDetailDTO} La viajero que se desea
     * guardar.
     * @return JSON {@link ViajeroDetailDTO} - La viajero guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la viajero a
     * actualizar.
     */
    @PUT
    @Path("{viajerosId: \\d+}")
    public ViajeroDetailDTO updateViajero(@PathParam("viajerosId") Long viajerosId, ViajeroDetailDTO viajero) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ViajeroResource updateViajero: input: id:{0} , viajero: {1}", new Object[]{viajerosId, viajero});
        viajero.setId(viajerosId);
        if (logica.getViajero(viajerosId) == null) {
            throw new WebApplicationException("El recurso /viajeros/" + viajerosId + " no existe.", 404);
        }
        ViajeroDetailDTO detailDTO = new ViajeroDetailDTO(logica.updateViajero(viajerosId, viajero.toEntity()));
        LOGGER.log(Level.INFO, "ViajeroResource updateViajero: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la viajero con el id asociado recibido en la URL.
     *
     * @param viajerosId Identificador de la viajero que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la viajero.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la viajero.
     */
    @DELETE
    @Path("{viajerosId: \\d+}")
    public void deleteViajero(@PathParam("viajerosId") Long viajerosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViajeroResource deleteViajero: input: {0}", viajerosId);
        if (logica.getViajero(viajerosId) == null) {
            throw new WebApplicationException("El recurso /viajeros/" + viajerosId + " no existe.", 404);
        }
        logica.deleteViajero(viajerosId);
        LOGGER.info("ViajeroResource deleteViajero: output: void");
    }
    @Path("{viajerosId: \\d+}/calificaciones")
    public Class<ViajeroCalificacionResource> getCalificacionesResource(@PathParam("viajerosId") Long viajerosId) {
        if (logica.getViajero(viajerosId) == null) {
            throw new WebApplicationException("El recurso /conductor/" + viajerosId + " no existe.", 404);
        }
        return ViajeroCalificacionResource.class;
    }

    /**
     * Conexión con el servicio de libros para una viajero.
     * {@link ViajeroReservaResource}
     *
     * Este método conecta la ruta de /viajeros con las rutas de /reservas que
     * dependen de la viajero, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una viajero.
     *
     * @param viajerosId El ID de la viajero con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta viajero en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la viajero.
    */
   /// @Path("{viajerosId: \\d+}/reservas")
   // public Class<ViajeroReservaResource> getViajeroReservaResource(@PathParam("viajerosId") Long viajerosId) {
    //    if (logica.getViajero(viajerosId) == null) {
     //       throw new WebApplicationException("El recurso /viajeros/" + viajerosId + " no existe.", 404);
      //  }
     //   return ViajeroReservaResource.class;
  //  }

     @Path("{viajerosId: \\d+}/notificaciones")
    public Class<ViajeroNotificacionResource> getNotificacionesResource(@PathParam("viajerosId") Long viajerosId) {
        if (logica.getViajero(viajerosId) == null) {
            throw new WebApplicationException("El recurso /viajero/" + viajerosId + " no existe.", 404);
        }
        return ViajeroNotificacionResource.class;
    }
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ViajeroEntity a una lista
     * de objetos ViajeroDetailDTO (json)
     *
     * @param entityList corresponde a la lista de viajeros de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de viajeros en forma DTO (json)
     */
    private List<ViajeroDetailDTO> listEntity2DetailDTO(List<ViajeroEntity> entityList) {
        List<ViajeroDetailDTO> list = new ArrayList<>();
        for (ViajeroEntity entity : entityList) {
            list.add(new ViajeroDetailDTO(entity));
        }
        return list;
    }
}
