/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.resources;

import co.edu.uniandes.csw.carpooling.dtos.PublicidadDTO;
import co.edu.uniandes.csw.carpooling.ejb.PublicidadLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
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

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PublicidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(PublicidadResource.class.getName());
    
    @Inject
    private PublicidadLogic logica;
    
        /**
     * Crea una nueva reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param publicistasId El ID del publicista del cual se le agrega la reseña
     * @param publicidad {@link PublicidadDTO} - La publicidad que se desea guardar.
     * @return JSON {@link PublicidadDTO} - La publicidad guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     */
    @POST
    public PublicidadDTO createPublicidad(@PathParam("publicistasId") Long publicistasId, PublicidadDTO publicidad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PublicidadResource createPublicidad: input: {0}", publicidad);
        PublicidadDTO nuevaPublicidadDTO = new PublicidadDTO(logica.createPublicidad(publicistasId,publicidad.toEntity()));
        LOGGER.log(Level.INFO, "PublicidadResource createPublicidad: output: {0}", nuevaPublicidadDTO);
        return nuevaPublicidadDTO;
    }
    
        /**
     * Busca y devuelve todas las reseñas que existen en un libro.
     *
     * @param publicistasId El ID del libro del cual se buscan las reseñas
     * @return JSONArray {@link PublicidadDTO} - Las reseñas encontradas en el
     * libro. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<PublicidadDTO> getPublicidades(@PathParam("publicistasId") Long publicistasId) {
        LOGGER.log(Level.INFO, "PublicidadResource getPublicidades: input: {0}", publicistasId);
        
        List<PublicidadDTO> listaPublicidades = listEntity2DTO(logica.getPublicidades(publicistasId));
        
        LOGGER.log(Level.INFO, "PublicidadResource getPublicidades: output: {0}", listaPublicidades);
        return listaPublicidades;
    }
    
    /**
     * Busca y devuelve la reseña con el ID recibido en la URL, relativa a un
     * libro.
     *
     * @param publicistasId El ID del libro del cual se buscan las reseñas
     * @param publicidadesId El ID de la reseña que se busca
     * @return {@link PublicidadDTO} - La reseña encontradas en el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @GET
    @Path("{publicidadesId: \\d+}")
    public PublicidadDTO getPublicidad(@PathParam("publicistasId") Long publicistasId, @PathParam("publicidadesId") Long publicidadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PublicidadResource getPublicidad: input: {0}", publicidadesId);
        PublicidadEntity entity = logica.getPublicidad(publicistasId, publicidadesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /publicistas/" + publicistasId + "/publicidades/" + publicidadesId + " no existe.", 404);
        }
        PublicidadDTO publicidadDTO = new PublicidadDTO(entity);
        LOGGER.log(Level.INFO, "PublicidadResource getPublicidad: output: {0}", publicidadDTO);
        return publicidadDTO;
    }

    /**
     * Actualiza una reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param publicistasId El ID del libro del cual se guarda la reseña
     * @param publicidadesId El ID de la reseña que se va a actualizar
     * @param publicidad {@link PublicidadDTO} - La reseña que se desea guardar.
     * @return JSON {@link PublicidadDTO} - La reseña actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @PUT
    @Path("{publicidadesId: \\d+}")
    public PublicidadDTO updatePublicidad(@PathParam("publicistasId") Long publicistasId, @PathParam("publicidadesId") Long publicidadesId, PublicidadDTO publicidad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PublicidadResource updatePublicidad: input: publicistasId: {0} , publicidadesId: {1} , publicidad:{2}", new Object[]{publicistasId, publicidadesId, publicidad});
        if (publicidadesId.equals(publicidad.getId())) {
            throw new BusinessLogicException("Los ids del Publicidad no coinciden.");
        }
        PublicidadEntity entity = logica.getPublicidad(publicistasId, publicidadesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /publicistas/" + publicistasId + "/publicidades/" + publicidadesId + " no existe.", 404);

        }
        PublicidadDTO publicidadDTO = new PublicidadDTO(logica.updatePublicidad(publicistasId, publicidad.toEntity()));
        LOGGER.log(Level.INFO, "PublicidadResource updatePublicidad: output:{0}", publicidadDTO);
        return publicidadDTO;

    }

    /**
     * Borra la reseña con el id asociado recibido en la URL.
     *
     * @param publicistasId El ID del libro del cual se va a eliminar la reseña.
     * @param publicidadesId El ID de la reseña que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @DELETE
    @Path("{publicidadesId: \\d+}")
    public void deletePublicidad(@PathParam("publicistasId") Long publicistasId, @PathParam("publicidadesId") Long publicidadesId) throws BusinessLogicException {
        PublicidadEntity entity = logica.getPublicidad(publicistasId, publicidadesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /publicistas/" + publicistasId + "/publicidades/" + publicidadesId + " no existe.", 404);
        }
        logica.deletePublicidad(publicistasId, publicidadesId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos PublicidadDTO (json)
     *
     * @param entityList corresponde a la lista de reseñas de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de reseñas en forma DTO (json)
     */
    private List<PublicidadDTO> listEntity2DTO(List<PublicidadEntity> entityList) {
        List<PublicidadDTO> list = new ArrayList<>();
        for (PublicidadEntity entity : entityList) {
            list.add(new PublicidadDTO(entity));
        }
        return list;
    }
}
