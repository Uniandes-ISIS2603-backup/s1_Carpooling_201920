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
        LOGGER.log(Level.INFO, "ReviewResource createPublicidad: output: {0}", nuevaPublicidadDTO);
        return nuevaPublicidadDTO;
    }
    
    @GET
    public List<PublicidadDTO> getPublicidades(){
        List<PublicidadDTO> publicidades = listaPublicidadesEntityToDTO(logica.getPublicidades());
        return publicidades;
    }
    
    @GET
    @Path("(publicidadesId: \\d+)")
    public PublicidadDTO getViaje(@PathParam("publicidadesId") Long publicidadesId){
        PublicidadEntity publicidadEntity = logica.getPublicidad(publicidadesId);
        if(publicidadEntity == null){
            throw new WebApplicationException("El recurso /publicidades/"+publicidadesId+ " no existe",404 );
        }
        PublicidadDTO dto = new PublicidadDTO(publicidadEntity);
        return dto;
    }
    
    @PUT
    @Path("(publicidadesId: \\d+)")
    public PublicidadDTO updatePublicidad(@PathParam("publicidadesId") Long publicidadesId,PublicidadDTO publicidad)throws BusinessLogicException{
        publicidad.setId(publicidadesId);
        if(logica.getPublicidad(publicidadesId)==null){
            throw new WebApplicationException("El recurso /publicidades/"+publicidadesId+" no existe",404);
        }
        PublicidadDTO dto = new PublicidadDTO(logica.updatePublicidad(publicidad.toEntity()));
        return dto;
    }
    
    @DELETE
    @Path("(publicidadesId: \\d+)")
    public void deletePublicidad(@PathParam("publicidadesId") Long publicidadesId)throws BusinessLogicException{
        if(logica.getPublicidad(publicidadesId)==null){
            throw new WebApplicationException("El recurso /publicidades/"+publicidadesId+" no existe",404);
        }
        logica.deletePublicidad(publicidadesId);
    }
    
    private List<PublicidadDTO> listaPublicidadesEntityToDTO(List<PublicidadEntity> publicidades){
        List<PublicidadDTO> publicidadesDTO =new ArrayList<PublicidadDTO>(); 
        for(PublicidadEntity entidad : publicidades){
            publicidadesDTO.add(new PublicidadDTO(entidad));
        }
        return publicidadesDTO;
    }
    
}
