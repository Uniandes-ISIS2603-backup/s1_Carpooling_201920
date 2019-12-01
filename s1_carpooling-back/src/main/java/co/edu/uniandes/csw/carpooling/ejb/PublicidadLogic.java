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
    
    /**
     * La persistencuia de la publicidad
     */
     @Inject
    private PublicidadPersistence persistence;
    
     /**
      * la persistencia del publicista
      */
    @Inject
    private PublicistaPersistence publicistaPersistence;
    
    /**
     * el logger
     */
    private static final Logger LOGGER = Logger.getLogger(PublicidadLogic.class.getName());
        
    
    /**
     * Crea una publicidad en la base de datos
     * @param publicistaId el id del publicista duenho de la publicidad
     * @param publicidadEntity la publicidad a crear
     * @return la publicidad creada
     * @throws BusinessLogicException si la publicidad creada no sigue con alguna de las reglas del negocio
     */
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
        PublicistaEntity publi = publicistaPersistence.find(publicistaId);
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
    
    /**
     * Actualiza una publicidad
     * @param publicistasId el id del publicista duenho de la publicidad a actualizar
     * @param publicidadEntity la publicidad actualizada
     * @return la publicidad actualizada
     * @throws BusinessLogicException  si la publicidad actualizada no cumple con alguna de las reglas de negocio
     */
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
    
    
    /**
     * valida si el nombre de la publicidad cumple con las reglas de negocio
     * @param nombre el nombre a validar
     * @return true si el nombre sigue las reglas de negocio, false de lo contrario
     */
    private boolean validarNombre(String nombre){
        return !(nombre == null || nombre.isEmpty());
    }
    
    /**
     * valida si el mensaje de la publicidad cumple con las reglas de negocio
     * @param mensaje el mensaje a validar
     * @return true si el mensaje sigue las reglas de negocio, false de lo contrario
     */
    private boolean validarMensaje(String mensaje){
        return !(mensaje == null|| mensaje.isEmpty());
    }
    
    /**
     * valida si el costo de la publicidad es valido
     * @param costo el costo a validar
     * @return true si el costo de la publicidad sigue las reglas de negocio, false de lo contrario
     */
    private boolean validarCosto(double costo){
        return (costo>=0);
    }
    
    /**
     * valida si las fechas de la publicidad cumplen con las reglas de negocio
     * @param fechaInicio la fecha de inicio a validar
     * @param fechaSalida la fecha de salida a validar
     * @return true si las fechsa cumplen con las reglas de negocio, false de lo contrario
     */
    private boolean validarFechas(Date fechaInicio, Date fechaSalida){
        return !(fechaInicio==null||fechaSalida==null||fechaInicio.compareTo(fechaSalida)>0);
    }
    
    
    /**
     * Valida si una publicidad cumple con las reglas de negocio
     * @param entidad la publicidad a validar
     * @return true si la publicidad a validar cumple con las reglas de negocio, false de lo contrario
     */
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
