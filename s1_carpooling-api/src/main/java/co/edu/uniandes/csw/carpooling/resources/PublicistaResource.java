/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.PublicistaDTO;
import co.edu.uniandes.csw.carpooling.dtos.PublicistaDetailDTO;
import co.edu.uniandes.csw.carpooling.ejb.PublicistaLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
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
@Path("/publicistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PublicistaResource {

    private static final Logger LOGGER = Logger.getLogger(PublicistaResource.class.getName());

    @Inject
    private PublicistaLogic logica;

    @POST
    public PublicistaDTO createPublicista(PublicistaDTO publicista) throws BusinessLogicException {

        PublicistaEntity publicistaEntity = publicista.toEntity();
        publicistaEntity = logica.createPublicista(publicistaEntity);
        return new PublicistaDTO(publicistaEntity);

    }

    @GET
    @Path("{publicistasId: \\d+}")
    public PublicistaDetailDTO getPublicista(@PathParam("publicistasId") Long publicistasId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "PublicistaResource getPublicista: input: {0}", publicistasId);
        // Me devuelve la entidad por su id
        PublicistaEntity publicistaEntity = logica.getPublicista(publicistasId);

        // Si el objeto no existe significa que es nulo
        if (publicistaEntity == null) {
            throw new WebApplicationException("El recurso /publicistas/" + publicistasId + " no existe.", 404);
        }
        PublicistaDetailDTO detailDTO = new PublicistaDetailDTO(publicistaEntity);
        LOGGER.log(Level.INFO, "PublicistaResource getPublicista: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos las publicistas que existen en la aplicacion.
     *
     * @return JSONArray {@link PublicistaDetailDTO} - Las publicistas
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<PublicistaDetailDTO> getPublicistas() {
        LOGGER.info("PublicistaResource getPublicistas: input: void");
        List<PublicistaDetailDTO> listaPublicistas = listEntity2DetailDTO(logica.getPublicistas());
        LOGGER.log(Level.INFO, "PublicistaResource getPublicistas: output: {0}", listaPublicistas);
        return listaPublicistas;
    }

    

    /**
     * Actualiza la publicista con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param publicistasId Identificador de la publicista que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param publicista {@link PublicistaDetailDTO} La publicista que se desea
     * guardar.
     * @return JSON {@link PublicistaDetailDTO} - La publicista guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la publicista a
     * actualizar.
     */
    @PUT
    @Path("{publicistasId: \\d+}")
    public PublicistaDetailDTO updatePublicista(@PathParam("publicistasId") Long publicistasId, PublicistaDetailDTO publicista) throws WebApplicationException {
        LOGGER.log(Level.INFO, "PublicistaResource updatePublicista: input: id:{0} , publicista: {1}", new Object[]{publicistasId, publicista});
        publicista.setId(publicistasId);
        if (logica.getPublicista(publicistasId) == null) {
            throw new WebApplicationException("El recurso /publicistas/" + publicistasId + " no existe.", 404);
        }
        PublicistaDetailDTO detailDTO = new PublicistaDetailDTO(logica.updatePublicista(publicistasId, publicista.toEntity()));
        LOGGER.log(Level.INFO, "PublicistaResource updatePublicista: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la publicista con el id asociado recibido en la URL.
     *
     * @param publicistasId Identificador de la publicista que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la publicista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la publicista.
     */
    @DELETE
    @Path("{publicistasId: \\d+}")
    public void deletePublicista(@PathParam("publicistasId") Long publicistasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PublicistaResource deletePublicista: input: {0}", publicistasId);
        if (logica.getPublicista(publicistasId) == null) {
            throw new WebApplicationException("El recurso /publicistas/" + publicistasId + " no existe.", 404);
        }
        logica.deletePublicista(publicistasId);
        LOGGER.info("PublicistaResource deletePublicista: output: void");
    }

    /**
     * Conexión con el servicio de libros para una publicista.
     * {@link PublicistaPublicidadResource}
     *
     * Este método conecta la ruta de /publicistas con las rutas de /publicidades que
     * dependen de la publicista, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una publicista.
     *
     * @param publicistasId El ID de la publicista con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta publicista en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la publicista.
    */ 
    @Path("{publicistasId: \\d+}/publicidades")
    public Class<PublicistaPublicidadResource> getPublicistaPublicidadResource(@PathParam("publicistasId") Long publicistasId) {
        if (logica.getPublicista(publicistasId) == null) {
            throw new WebApplicationException("El recurso /publicistas/" + publicistasId + " no existe.", 404);
        }
        return PublicistaPublicidadResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PublicistaEntity a una lista
     * de objetos PublicistaDetailDTO (json)
     *
     * @param entityList corresponde a la lista de publicistas de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de publicistas en forma DTO (json)
     */
    private List<PublicistaDetailDTO> listEntity2DetailDTO(List<PublicistaEntity> entityList) {
        List<PublicistaDetailDTO> list = new ArrayList<>();
        for (PublicistaEntity entity : entityList) {
            list.add(new PublicistaDetailDTO(entity));
        }
        return list;
    }
}
