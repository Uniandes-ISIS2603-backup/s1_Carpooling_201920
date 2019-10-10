/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;
import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajeroPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
        

/**
 *
 * @author Juan David Alarcon
 */

@Stateless
public class CalificacionLogic {
    @Inject
    private CalificacionPersistence persistence;
    @Inject
    private ViajeroPersistence viajeroPersistence;
    @Inject
    private ConductorPersistence conductorPersistence;
    
    
    
    public CalificacionEntity createCalificacion(CalificacionEntity calificacion ) throws BusinessLogicException
    {
        if(calificacion.getComentarios().length()> 500)
        {
            throw new BusinessLogicException("El comentario no puede ser mayor a 500 caracteres");
        }
        
        if(calificacion.getPuntuacion() < 0 || calificacion.getPuntuacion() > 5)
        {
            throw new BusinessLogicException("La puntuacion no puede ser menor a cero o mayor a 5");
        }
        
     calificacion = persistence.create(calificacion);
     return calificacion;
    }
    
    public CalificacionEntity createCalificacionForViajero(Long viajeroId,CalificacionEntity calificacion ) throws BusinessLogicException
    {
        if(calificacion.getComentarios().length()> 500)
        {
            throw new BusinessLogicException("El comentario no puede ser mayor a 500 caracteres");
        }
        
        if(calificacion.getPuntuacion() < 0 || calificacion.getPuntuacion() > 5)
        {
            throw new BusinessLogicException("La puntuacion no puede ser menor a cero o mayor a 5");
        }
     
        ViajeroEntity viajero = viajeroPersistence.find(viajeroId);
        calificacion.setViajero(viajero);
        calificacion.setConductor(null);
        calificacion = persistence.create(calificacion);
        return calificacion;
    }
    
    public CalificacionEntity createCalificacionForConductor(Long conductorId,CalificacionEntity calificacion ) throws BusinessLogicException
    {
        if(calificacion.getComentarios().length()> 500)
        {
            throw new BusinessLogicException("El comentario no puede ser mayor a 500 caracteres");
        }
        
        if(calificacion.getPuntuacion() < 0 || calificacion.getPuntuacion() > 5)
        {
            throw new BusinessLogicException("La puntuacion no puede ser menor a cero o mayor a 5");
        }
     
        ConductorEntity conductor = conductorPersistence.find(conductorId);
        calificacion.setConductor(conductor);
        calificacion.setViajero(null);
        calificacion = persistence.create(calificacion);
        return calificacion;
    }
    
    public List<CalificacionEntity> getCalificacionesByViajero(Long viajerosId) {
        ViajeroEntity viajeroEntity = viajeroPersistence.find(viajerosId);
        return viajeroEntity.getCalificaciones();
    }
    public List<CalificacionEntity> getCalificacionesByConductor(Long conductoresId) {
        ConductorEntity conductorEntity = conductorPersistence.find(conductoresId);
        return conductorEntity.getCalificaciones();
    }
    
    public CalificacionEntity getCalificacionByViajero(Long viajerosId, Long calificacionesId) {
        return persistence.findByViajero(viajerosId, calificacionesId);
    }
    
    public CalificacionEntity getCalificacionByConductor(Long conductoresId, Long calificacionesId) {
        return persistence.findByConductor(conductoresId, calificacionesId);
    }
    
    public CalificacionEntity updateCalificacionByViajero(Long viajerosId, CalificacionEntity calificacionEntity) {
        ViajeroEntity viajeroEntity = viajeroPersistence.find(viajerosId);
        calificacionEntity.setViajero(viajeroEntity);
        persistence.update(calificacionEntity);
        return calificacionEntity;
    }
    
    public CalificacionEntity updateCalificacionByConductor(Long conductoresId, CalificacionEntity calificacionEntity) {
        ConductorEntity conductorEntity = conductorPersistence.find(conductoresId);
        calificacionEntity.setConductor(conductorEntity);
        persistence.update(calificacionEntity);
        return calificacionEntity;
    }
        
    public void deleteCalificacionByViajero(Long viajerosId, Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity old = getCalificacionByViajero(viajerosId, calificacionesId);
        if (old == null) {
            throw new BusinessLogicException("La calificacion con id = " + calificacionesId + " no esta asociada a el viajero con id = " + viajerosId);
        }
        persistence.delete(old.getId());
    }
    
    public void deleteCalificacionByConductor(Long conductoresId, Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity old = getCalificacionByConductor(conductoresId, calificacionesId);
        if (old == null) {
            throw new BusinessLogicException("La calificacion con id = " + calificacionesId + " no esta asociada a el conductor con id = " + conductoresId);
        }
        persistence.delete(old.getId());
    }
        
        
        
    
    
    
}