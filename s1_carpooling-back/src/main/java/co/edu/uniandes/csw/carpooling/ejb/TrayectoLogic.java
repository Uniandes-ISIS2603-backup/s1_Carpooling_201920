/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.TrayectoPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajePersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Trayecto.
 * @author Juan David Serrano
 */
@Stateless
public class TrayectoLogic {

    private static final Logger LOGGER = Logger.getLogger(TrayectoLogic.class.getName());

    @Inject
    private TrayectoPersistence persistence;

    @Inject
    private ViajePersistence viajePersistence;

    /**
     * Crea una instancia Trayeto de la base de datos de un viaje en especifico
     *
     * @param viajesId  Viaje dueño del trayecto a crear
     * @param trayecto  Trayecto que se desea crear
     * @return Trayecto creado con id asignado
     * @throws BusinessLogicException numero de peajes invalido
     * duracion invalida
     * costo combustible erroneo
     * origen o destino invalido
     */
    public TrayectoEntity createTrayecto(Long viajesId, TrayectoEntity trayecto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear trayecto");
        if (!validateNumPeajes(trayecto.getNumPeajes())) {
            throw new BusinessLogicException("Numero de peajes erroneo");
        } else if (!validateDuracion(trayecto.getDuracion())) {
            throw new BusinessLogicException("Duracion no valida ");
        } else if (!validateCostoCombustible(trayecto.getCostoCombustible())) {
            throw new BusinessLogicException("Costo de combustible erroneo");
        } else if (!validateOrigen(trayecto.getOrigen()) || !validateDestino(trayecto.getDestino()))// || trayecto.getDestino().equals(trayecto.getOrigen()))
        {
            throw new BusinessLogicException("Origen o destinos erroneos");
        }
        ViajeEntity viajeEntity = viajePersistence.find(viajesId);
        trayecto.setViaje(viajeEntity);
        trayecto = persistence.create(trayecto);
        LOGGER.log(Level.INFO, "Termina proceso de creación del trayecto");
        return trayecto;
    }

    /**
     * Retorna los trayectos de la base de datos de un viaje en especifico
     *
     * @param viajesId  Viaje dueño del trayectos
     * @return LisTrayecto si existe con esas caracteristicas o null si no existe
     */
    public List<TrayectoEntity> getTrayectos(Long viajesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los trayectos asociados al viaje con id = {0}", viajesId);
        ViajeEntity viajeEntity = viajePersistence.find(viajesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los trayectos asociados al viaje con id = {0}", viajesId);
        return viajeEntity.getTrayectos();
    }

    /**
     * Retorna una instancia de Trayecto de la base de datos de un viaje en especifico
     *
     * @param trayectoId Instancia a actualizar
     * @param viajeId  Viaje dueño del trayecto
     * @return Trayecto si existe con esas caracteristicas o null si no existe
     */
    public TrayectoEntity getTrayecto(Long trayectoId, Long viajeId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el trayecto con id = {0}", trayectoId);
        return persistence.find(trayectoId, viajeId);
    }

    /**
     * Actualiza una instancia de Trayecto de la base de datos de un viaje en especifico
     *
     * @param trayecto Instancia a actualizar
     * @param viajesId  Viaje dueño del trayecto
     * @throws BusinessLogicException si no exite instancia trayectos con esas caractersiticas.
     */
    public TrayectoEntity updateTrayecto(TrayectoEntity trayecto, Long viajesId) throws BusinessLogicException {
        if (!validateNumPeajes(trayecto.getNumPeajes())) {
            throw new BusinessLogicException("Numero de peajes erroneo");
        } else if (!validateDuracion(trayecto.getDuracion())) {
            throw new BusinessLogicException("Duracion no valida ");
        } else if (!validateCostoCombustible(trayecto.getCostoCombustible())) {
            throw new BusinessLogicException("Costo de combustible erroneo");
        } else if (!validateOrigen(trayecto.getOrigen()) || !validateDestino(trayecto.getDestino()))// || trayecto.getDestino().equals(trayecto.getOrigen()))
        {
            throw new BusinessLogicException("Origen o destinos erroneos");
        }
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el trayecto con id = {0} del viaje con id = {1}", new Object[]{trayecto.getId(), viajesId});
        ViajeEntity viajeEntity = viajePersistence.find(viajesId);
        trayecto.setViaje(viajeEntity);
        TrayectoEntity nuevo = persistence.update(trayecto);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el trayecto con id = {0} del viaje con id = {1}", new Object[]{trayecto.getId(), viajesId});
        return nuevo;
    }

    /**
     * Elimina una instancia de Trayecto de la base de datos de un viaje en especifico
     *
     * @param trayectoId Identificador de la instancia a eliminar 
     * @param viajesId  Viaje dueño del trayecto
     * @throws BusinessLogicException si no exite instancia trayecto con esas caractersiticas.
     */
    public void deleteTrayecto(Long trayectoId, Long viajesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el trayecto con id = {0} del viaje con id = {1}", new Object[]{trayectoId, viajesId});
        TrayectoEntity trayecto = getTrayecto(trayectoId, viajesId);
        if (trayecto == null) {
            throw new BusinessLogicException("El trayecto con id = " + trayectoId + "no esta asociado con el viaje con id =" + viajesId);
        }
        persistence.delete(trayectoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el trayecto con id = {0} del viaje con id = {1}", new Object[]{trayectoId, viajesId,});
    }

    /**
     * Verifica que el numero de peajes no sea invalido.
     *
     * @param numPeajes a verificar
     * @return true si el numero de peajes es valido.
     */
    private boolean validateNumPeajes(Integer numPeajes) {
        return numPeajes != null && numPeajes >= 0;
    }

    /**
     * Verifica que la duracion no sea invalido.
     *
     * @param duracion a verificar
     * @return true si la duracion es valido.
     */
    private boolean validateDuracion(Integer duracion) {
        return duracion != null && duracion >= 1;
    }

    /**
     * Verifica que el costo combustible no sea invalido.
     *
     * @param costoCombustible a verificar
     * @return true si el costoCombustible es valido.
     */
    private boolean validateCostoCombustible(Double costoCombustible) {
        return costoCombustible != null && costoCombustible >= 0;
    }

    /**
     * Verifica que el origen no sea invalido.
     *
     * @param origen a verificar
     * @return true si el origen es valido.
     */
    private boolean validateOrigen(String origen) {
        return origen != null && !origen.isEmpty();
    }

    /**
     * Verifica que el destino no sea invalido.
     *
     * @param destino a verificar
     * @return true si el destino es valido.
     */
    private boolean validateDestino(String destino) {
        return destino != null && !destino.isEmpty();
    }

}
