/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicidadPersistence;
import co.edu.uniandes.csw.carpooling.persistence.PublicistaPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolas Fajardo Ramirez
 */
@Stateless
public class PublicidadLogic {
    
     @Inject
    private PublicidadPersistence persistence;
    
    @Inject
    private PublicistaPersistence publicistaPersistence;
    
    private static final Logger LOGGER = Logger.getLogger(PublicidadLogic.class.getName());
        
    public PublicidadEntity createPublicidad(Long publicistaId,PublicidadEntity publicidadEntity) throws BusinessLogicException{
      
        LOGGER.log(Level.INFO, "Inicia proceso de crear publicidad");
       
        if(!validarPublicidad(publicidadEntity)){
            throw new BusinessLogicException("Ya hay una publicidad con ese nombre en ese rango de fechas");
        }
        else if(!validarNombre(publicidadEntity.getNombre())){
            throw new BusinessLogicException("El nombre es invalido");
        }
        else if(!validarMensaje(publicidadEntity.getMensaje())){
            throw new BusinessLogicException("El mensaje es invalido");
        }
        else if(!validarCosto(publicidadEntity.getCosto())){
            throw new BusinessLogicException("El costo es invalido");
        }
        else if(!validarFechas(publicidadEntity.getFechaDeInicio(), publicidadEntity.getFechaDeSalida())){
            throw new BusinessLogicException("Las fechas son invalidas");
        }
        
        PublicistaEntity publicista = publicistaPersistence.find(publicistaId);
        publicidadEntity.setPublicista(publicista);
        
        LOGGER.log(Level.INFO, "Termina proceso de creación del publicidad");
        
        return persistence.create(publicidadEntity);
    }
    public PublicidadEntity createPublicidad(Long publicistaId, PublicidadEntity publicidadEntity) throws BusinessLogicException{
        if(!validarPublicidad(publicidadEntity)){
            throw new BusinessLogicException("Ya hay una publicidad con ese nombre en ese rango de fechas");
        }
        else if(!validarNombre(publicidadEntity.getNombre())){
            throw new BusinessLogicException("El nombre es invalido");
        }
        else if(!validarMensaje(publicidadEntity.getMensaje())){
            throw new BusinessLogicException("El mensaje es invalido");
        }
        else if(!validarCosto(publicidadEntity.getCosto())){
            throw new BusinessLogicException("El costo es invalido");
        }
        else if(!validarFechas(publicidadEntity.getFechaDeInicio(), publicidadEntity.getFechaDeSalida())){
            throw new BusinessLogicException("Las fechas son invalidas");
        }
        PublicistaEntity publi = publicista.find(publicistaId);
        publicidadEntity.setPublicista(publi);
        PublicidadEntity retorno = persistence.create(publicidadEntity);
        return retorno;
    }
    
        /**
     * Obtiene la lista de los registros de Review que pertenecen a un Publicista.
     *
     * @param publicistasId id del Publicista el cual es padre de los Reviews.
     * @return Colección de objetos de ReviewEntity.
     */
    public List<PublicidadEntity> getPublicidades(Long publicistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los publicidades asociados al publicista con id = {0}", publicistasId);
        PublicistaEntity publicidadEntity = publicistaPersistence.find(publicistasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los publicidades asociados al publicista con id = {0}", publicistasId);
        return publicidadEntity.getPublicidades();
    }
    
    
    /**
     * Obtiene los datos de una instancia de Review a partir de su ID. La
     * existencia del elemento padre Publicista se debe garantizar.
     *
     * @param publicistasId El id del Libro buscado
     * @param publicidadesId Identificador de la Publicidad a consultar
     * @return Instancia de ReviewEntity con los datos del Review consultado.
     *
     */
    public PublicidadEntity getPublicidad(Long publicistasId, Long publicidadesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el publicidad con id = {0} del publicista con id = " + publicistasId, publicidadesId);
        return persistence.find(publicistasId, publicidadesId);
    }
    
    public PublicidadEntity updatePublicidad(Long publicistasId,PublicidadEntity publicidadEntity)throws BusinessLogicException{
        if(!validarPublicidad(publicidadEntity)){
            throw new BusinessLogicException("Ya hay una publicidad con ese nombre en ese rango de fechas");
        }
        else if(!validarNombre(publicidadEntity.getNombre())){
            throw new BusinessLogicException("El nombre es invalido");
        }
        else if(!validarMensaje(publicidadEntity.getMensaje())){
            throw new BusinessLogicException("El mensaje es invalido");
        }
        else if(!validarCosto(publicidadEntity.getCosto())){
            throw new BusinessLogicException("El costo es invalido");
        }
        else if(!validarFechas(publicidadEntity.getFechaDeInicio(), publicidadEntity.getFechaDeSalida())){
            throw new BusinessLogicException("Las fechas son invalidas");
        }
        PublicistaEntity publicistaEntity = publicistaPersistence.find(publicistasId);
        publicidadEntity.setPublicista(publicistaEntity);
        return persistence.update(publicidadEntity);
    }
    
        /**
     * Elimina una instancia de Review de la base de datos.
     *
     * @param publicidadesId Identificador de la instancia a eliminar.
     * @param publicistasId id del Publicista el cual es padre del Review.
     * @throws BusinessLogicException Si la publicidad no esta asociada al publicista.
     *
     */
    public void deletePublicidad(Long publicistasId, Long publicidadesId) throws BusinessLogicException {
        PublicidadEntity old = getPublicidad(publicistasId, publicidadesId);
        if (old == null) {
            throw new BusinessLogicException("El publicidad con id = " + publicidadesId + " no esta asociado a el publicista con id = " + publicistasId);
        }
        persistence.delete(old.getId());
    }
    
    
    private boolean validarNombre(String nombre){
        return !(nombre == null || nombre.isEmpty());
    }
    private boolean validarMensaje(String mensaje){
        return !(mensaje == null|| mensaje.isEmpty());
    }
    private boolean validarCosto(double costo){
        return (costo>=0);
    }
    private boolean validarFechas(Date fechaInicio, Date fechaSalida){
        return !(fechaInicio==null||fechaSalida==null||fechaInicio.compareTo(fechaSalida)>0);
    }
    private boolean validarPublicidad(PublicidadEntity entidad){
        String nombre = entidad.getNombre();
        boolean retorno = true;
        PublicidadEntity entidad2 = persistence.findByName(nombre);
        if((entidad2 != null)&&((entidad.getFechaDeInicio().compareTo(entidad2.getFechaDeInicio())>0 && entidad.getFechaDeInicio().compareTo(entidad2.getFechaDeSalida())<0) || entidad.getFechaDeSalida().compareTo(entidad2.getFechaDeInicio())>0 && entidad.getFechaDeSalida().compareTo(entidad2.getFechaDeSalida())<0)){
                    retorno = false;
                }
    
        return retorno;
    }
}
