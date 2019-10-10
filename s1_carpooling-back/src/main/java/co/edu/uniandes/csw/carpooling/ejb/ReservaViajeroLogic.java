/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ReservaPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajeroPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Reserva y Viajero
 *
 * @author le.perezl
 */
@Stateless
public class ReservaViajeroLogic {

    private static final Logger LOGGER = Logger.getLogger(ReservaViajeroLogic.class.getName());

    @Inject
    private ViajeroPersistence viajeroPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ReservaPersistence reservaPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar una reserva a un viajero
     *
     * @param reservaId El id reserva a guardar
     * @param viajeroId El id del viajero al cual se le va a guardar la reserva.
     * @return reserva que fue agregada al viajero.
     */
    public ReservaEntity addReserva(Long reservaId, Long viajeroId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el reserva con id = {0} al viajero con id = " + viajeroId, reservaId);
        ReservaEntity reservaEntity = reservaPersistence.find(reservaId);
        ViajeroEntity viajeroEntity = viajeroPersistence.find(viajeroId);
        List<ReservaEntity> reservas=  viajeroEntity.getReservas();
        reservas.add(reservaEntity);
        viajeroEntity.setReservas(reservas);
        LOGGER.log(Level.INFO, "Termina proceso de asociar la reserva con id = {0} al viajero con id = " + viajeroId, reservaId);
        return reservaPersistence.find(reservaId);
    }

    /**
     *
     * Obtener un premio por medio de su id y el de su autor.
     *
     * @param prizesId id del premio a ser buscado.
     * @return el autor solicitada por medio de su id.
     */
        public  List<ReservaEntity> getReserva(Long viajeroId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor del premio con id = {0}", viajeroId);
        List<ReservaEntity> reservas = viajeroPersistence.find(viajeroId).getReservas();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el autor del premio con id = {0}", viajeroId);
        return reservas;
    }

    /**
     * Remplazar autor de un premio
     *
     * @param viajeroId el id del viajero que se quiere actualizar.
     * @param reservaId El id del nueva reserva asociado al viajero.
     * @return el nuevo autor asociado.
     */
    public ReservaEntity replaceReserva(Long viajeroId, Long reservaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la reserva del viajero con id = {0}", viajeroId);
        ReservaEntity reservaEntity = reservaPersistence.find(reservaId);
        ViajeroEntity viajeroEntity = viajeroPersistence.find(viajeroId);
        List<ReservaEntity> reservas= viajeroEntity.getReservas();
        Boolean encontro=false;
        for(int i=0; i < reservas.size(); i++ ){
            if(reservaId== reservas.get(i).getId()){
                reservas.set(i, reservaEntity);
                encontro=true;
            }
        }
        if(encontro == false){
            throw new BusinessLogicException("El viajero no tiene esta reserva");
        }
        viajeroEntity.setReservas(reservas);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el autor con id = {0} al viajeroId con id = " + reservaId, viajeroId);
        return reservaPersistence.find(reservaId);
    }

    /**
     * Borrar el autor de un premio
     *
     * @param prizesId El premio que se desea borrar del autor.
     * @throws BusinessLogicException si el premio no tiene autor
     */
    public void removeReserva(Long viajeroId, Long reservaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar unba reserva del premio con id = {0}", viajeroId);
        ViajeroEntity viajeroEntity = viajeroPersistence.find(viajeroId);
        ReservaEntity reservaEntity = reservaPersistence.find(reservaId);
        List<ReservaEntity> reservas= viajeroEntity.getReservas();
        Boolean encontro=false;
        for(int i=0; i < reservas.size(); i++ ){
            if(reservaId== reservas.get(i).getId()){
                reservas.remove(i);
                encontro=true;
            }
        }
         if(encontro == false){
            throw new BusinessLogicException("El viajero no tiene esta reserva");
        }
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0} del premio con id = " + viajeroId, reservaEntity.getId());
    
    }
}