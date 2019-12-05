/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.persistence.NotificacionPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajeroPersistence;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author le.perezl
 */
@Stateless
public class NotificacionLogic {

    private static final Logger LOGGER = Logger.getLogger(NotificacionLogic.class.getName());

    @Inject
    private NotificacionPersistence persistence;
    @Inject
    private ViajeroPersistence viajeroPersistence;
    @Inject
    private ConductorPersistence conductorPersistence;

    public NotificacionEntity createNotificacion(NotificacionEntity notificacion) throws BusinessLogicException {
        if (notificacion.getFecha() == (null)) {
            throw new BusinessLogicException("La notificacion no tiene una fecha");
        }
        if (notificacion.getFecha().compareTo(Calendar.getInstance().getTime()) < 0) {
            throw new BusinessLogicException("La notificacion tiene una fecha menor a la actual");
        }
        if (notificacion.getMensaje() == (null)) {
            throw new BusinessLogicException("La notificacion no tiene un mensaje");
        }
        if (notificacion.getTitulo() == (null)) {
            throw new BusinessLogicException("La notificacion no tiene un mensaje");
        }
        notificacion = persistence.create(notificacion);
        return notificacion;
    }

    public NotificacionEntity deleteNotificacion(NotificacionEntity notificacion) {

        persistence.delete(notificacion.getId());
        return notificacion;

    }

    public NotificacionEntity updateNotificacion(Long notificacioId, NotificacionEntity notificacion) throws BusinessLogicException {
        if (notificacion.getFecha() == (null)) {
            throw new BusinessLogicException("La notificacion no tiene una fecha");
        }
        if (notificacion.getFecha().compareTo(Calendar.getInstance().getTime()) < 0) {
            throw new BusinessLogicException("La notificacion tiene una fecha menor a la actual");
        }
        if (notificacion.getMensaje() == (null)) {
            throw new BusinessLogicException("La notificacion no tiene un mensaje");
        }
        if (notificacion.getTitulo() == (null)) {
            throw new BusinessLogicException("La notificacion no tiene un mensaje");
        }
        notificacion = persistence.update(notificacion);
        return notificacion;
    }

    public NotificacionEntity findNotificacion(Long notificacionId) throws BusinessLogicException {
        NotificacionEntity notificacionEntity = persistence.find(notificacionId);
        if (notificacionEntity == null) {
            throw new BusinessLogicException("La notificacion no existe");
        }
        return notificacionEntity;
    }

    /**
     * Devuelve todos los libros que hay en la base de datos.
     *
     * @return Lista de entidades de tipo libro.
     */
    public List<NotificacionEntity> findNotificacions() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros");
        List<NotificacionEntity> notificacions = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los libros");
        return notificacions;
    }

    ///////////////////////////////////////fix
    public NotificacionEntity createNotificacionForViajero(Long viajeroId, NotificacionEntity notificacion) throws BusinessLogicException {
        if (notificacion.getMensaje().length() > 500) {
            throw new BusinessLogicException("El comentario no puede ser mayor a 500 caracteres");
        }

        ViajeroEntity viajero = viajeroPersistence.find(viajeroId);
        notificacion.setViajero(viajero);
        notificacion.setConductor(null);
        notificacion = persistence.create(notificacion);
        return notificacion;
    }

    public NotificacionEntity createNotificacionForConductor(Long conductorId, NotificacionEntity notificacion) throws BusinessLogicException {
        if (notificacion.getMensaje().length() > 500) {
            throw new BusinessLogicException("El comentario no puede ser mayor a 500 caracteres");
        }

        ConductorEntity conductor = conductorPersistence.find(conductorId);
        notificacion.setConductor(conductor);
        notificacion.setViajero(null);
        notificacion = persistence.create(notificacion);
        return notificacion;
    }

    public List<NotificacionEntity> getNotificacionesByViajero(Long viajerosId) {
        ViajeroEntity viajeroEntity = viajeroPersistence.find(viajerosId);
        return viajeroEntity.getNotificaciones();
    }

    public List<NotificacionEntity> getNotificacionesByConductor(Long conductoresId) {
        ConductorEntity conductorEntity = conductorPersistence.find(conductoresId);
        return conductorEntity.getNotificaciones();
    }

    public NotificacionEntity getNotificacionByViajero(Long viajerosId, Long calificacionesId) {
        return persistence.findByViajero(viajerosId, calificacionesId);
    }

    public NotificacionEntity getNotificacionByConductor(Long conductoresId, Long notificacionId) {
        return persistence.findByConductor(conductoresId, notificacionId);
    }

    public NotificacionEntity updateNotificacionByViajero(Long viajerosId, NotificacionEntity notificacionEntity) {
        ViajeroEntity viajeroEntity = viajeroPersistence.find(viajerosId);
        notificacionEntity.setViajero(viajeroEntity);
        persistence.update(notificacionEntity);
        return notificacionEntity;
    }

    public NotificacionEntity updateNotificacionByConductor(Long conductoresId, NotificacionEntity notificacionEntity) {
        ConductorEntity conductorEntity = conductorPersistence.find(conductoresId);
        notificacionEntity.setConductor(conductorEntity);
        persistence.update(notificacionEntity);
        return notificacionEntity;
    }

    public void deleteNotificacionByViajero(Long viajerosId, Long notificacionesId) throws BusinessLogicException {
        NotificacionEntity old = getNotificacionByViajero(viajerosId, notificacionesId);
        if (old == null) {
            throw new BusinessLogicException("La calificacion con id = " + notificacionesId + " no esta asociada a el viajero con id = " + viajerosId);
        }
        persistence.delete(old.getId());
    }

    public void deleteNotificacionByConductor(Long conductoresId, Long notificacionesId) throws BusinessLogicException {
        NotificacionEntity old = getNotificacionByConductor(conductoresId, notificacionesId);
        if (old == null) {
            throw new BusinessLogicException("La calificacion con id = " + notificacionesId + " no esta asociada a el conductor con id = " + conductoresId);
        }
        persistence.delete(old.getId());
    }
}
