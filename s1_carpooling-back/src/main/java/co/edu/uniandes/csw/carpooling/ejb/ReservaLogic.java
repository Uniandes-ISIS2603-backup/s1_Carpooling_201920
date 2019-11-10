/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ReservaPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajePersistence;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ReservaLogic {

    private static final Logger LOGGER = Logger.getLogger(ReservaLogic.class.getName());

    @Inject
    private ReservaPersistence persistence;
    private ViajePersistence viajePersistence;

    public ReservaEntity createReserva(Long viajeId, ReservaEntity reserva) throws BusinessLogicException {
        if (reserva.getFecha().compareTo(Calendar.getInstance().getTime()) < 0) {
            throw new BusinessLogicException("La reserva tiene una fecha menor a la actual");
        }
        // preguntar si existe el viaje (not null) no se como hacerlo, se debe hacer la relacion, manyToOne en el entity con la referencia a viajes?

        if (reserva.getEstado() == (null)) {
            throw new BusinessLogicException("La reserva no tiene estado");
        }
        if (viajePersistence.find(viajeId).getReservas().size()>=viajePersistence.find(viajeId).getCupos()){
            throw new BusinessLogicException("El viaje no tiene cupos");
        }
        if (persistence.find(reserva.getId()) != null) {
            throw new BusinessLogicException("La reserva ya existe");
        }
        if (reserva.getViaje().getCupos() <= 0) {
            throw new BusinessLogicException("La reserva ya existe");
        }
        reserva = persistence.create(reserva);
        viajePersistence.find(viajeId).getReservas().add(reserva);
        return reserva;
    }

    public List<ReservaEntity> getReservas() {
        return persistence.findAll();
    }

    public ReservaEntity deleteReserva(ReservaEntity reserva) {
        persistence.delete(reserva.getId());
        return reserva;

    }

    public ReservaEntity updateReserva(Long reservaId, ReservaEntity reserva) throws BusinessLogicException {
        if (persistence.find(reserva.getId()) == (null)) {
            throw new BusinessLogicException("La reserva no existe");
        }
        if (reserva.getFecha().compareTo(Calendar.getInstance().getTime()) < 0) {
            throw new BusinessLogicException("La reserva tiene una fecha menor a la actual");
        }
        // preguntar si existe el viaje (not null) no se como hacerlo, se debe hacer la relacion, manyToOne en el entity con la referencia a viajes?
        reserva = persistence.update(reserva);
        return reserva;
    }

    public ReservaEntity findReserva(Long reservaId) throws BusinessLogicException {
        ReservaEntity reservaEntity = persistence.find(reservaId);
        if (reservaEntity == null) {
            throw new BusinessLogicException("La reserva no existe");
        }
        return reservaEntity;
    }
    
    public ReservaEntity findReservaByViaje(Long reservaId, Long viajeId) throws BusinessLogicException {
        ReservaEntity reservaEntity = persistence.findByViaje(reservaId, viajeId);
        if (reservaEntity.equals(null)) {
            throw new BusinessLogicException("La reserva no existe");
        }
        return reservaEntity;
    }

//       public List<ReservaEntity> getReservas(){
//        return persistence.findAll();
//       }
    
    public List<ReservaEntity> getReservasByViaje(Long viajeId) {
        ViajeEntity viajeEntity = viajePersistence.find(viajeId);
        return viajeEntity.getReservas();
    }
    
    public List<ReservaEntity> findReservas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros");
        List<ReservaEntity> reservas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los libros");
        return reservas;
    }
}
