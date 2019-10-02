/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.NotificacionPersistence;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author le.perezl
 */
@Stateless
public class NotificacionLogic {
    
    
    @Inject
     private NotificacionPersistence persistence;
    
    public NotificacionEntity createNotificacion(NotificacionEntity notificacion) throws BusinessLogicException{
        if(notificacion.getFecha()==(null)){
            throw new BusinessLogicException("La notificacion no tiene una fecha");
        }
        if(notificacion.getFecha().compareTo(Calendar.getInstance().getTime())<0){
            throw new BusinessLogicException("La notificacion tiene una fecha menor a la actual");
        }
        if(notificacion.getMensaje()==(null)){
            throw new BusinessLogicException("La notificacion no tiene un mensaje");
        }
      notificacion = persistence.create(notificacion);
        return notificacion;
}
    
    public NotificacionEntity deleteNotificacion(NotificacionEntity notificacion){
        
        persistence.delete(notificacion.getId());
        return notificacion;
        
    }
    
    public NotificacionEntity updateNotificacion(NotificacionEntity notificacion) throws BusinessLogicException{
        if(notificacion.getFecha()==(null)){
            throw new BusinessLogicException("La notificacion no tiene una fecha");
        }
        if(notificacion.getFecha().compareTo(Calendar.getInstance().getTime())<0){
            throw new BusinessLogicException("La notificacion tiene una fecha menor a la actual");
        }
        if(notificacion.getMensaje()==(null)){
            throw new BusinessLogicException("La notificacion no tiene un mensaje");
        }
        notificacion = persistence.update(notificacion);
        return notificacion;
    }
    
    public NotificacionEntity findNotificacion(Long notificacionId)throws BusinessLogicException{
        NotificacionEntity notificacionEntity = persistence.find(notificacionId);
        if (notificacionEntity == null) {
            throw new BusinessLogicException("La notificacion no existe");
        }
        return notificacionEntity;
    }
}