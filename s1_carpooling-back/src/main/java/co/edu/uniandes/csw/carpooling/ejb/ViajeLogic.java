/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.persistence.VehiculoPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajePersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Viaje.
 * @author Juan David Serrano
 */
@Stateless
public class ViajeLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ViajeLogic.class.getName());

    @Inject
    private ViajePersistence persistence;

    @Inject
    private VehiculoPersistence persistenceVehiculo;

    @Inject
    private ConductorPersistence persistenceConductor;
    
    
    /**
     * Crea un nuevo viaje
     *
     * @param viaje La entidad de tipo Viaje del nuevo viaje a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Origen o destino no validos
     * Numero de cupos incorrecto
     * Fecha de salida invalida
     * Costo viaje invalido
     * Estado del viaje no es correcto
     * Vehiculo asignado no valido
     * Viaje sin conductor asignado
     */
    public ViajeEntity createViaje(ViajeEntity viaje) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del viaje");
        if (!validateDestino(viaje.getDestino()) || !validateOrigen(viaje.getOrigen()))// || viaje.getDestino().equals(viaje.getOrigen()))
        {
            throw new BusinessLogicException("El destino u origen son incorrectos");
        } else if (!validateCupos(viaje.getCupos())) {
            throw new BusinessLogicException("El numero de cupos incorrecto");
        } else if (!validateFechaDeSalida(viaje.getFechaDeSalida()) || !validateFechaDeLlegada(viaje.getFechaDeLlegada()))// || viaje.getFechaDeSalida().compareTo(viaje.getFechaDeLlegada())<0 )
        {
            throw new BusinessLogicException("La fecha de salida o llegada no es correcta");
        } else if (!validateCostoViaje(viaje.getCostoViaje())) {
            throw new BusinessLogicException("La costo del viaje no es correcto");
        } else if (!validateEstadoViaje(viaje.getEstadoViaje())) {
            throw new BusinessLogicException("El estado del viaje no es correcto");
        } else if (!validateVehiculo(viaje.getVehiculo())) {
            throw new BusinessLogicException("El vehiculo asignado a este viaje no existe");
        } else if (!validateConductor(viaje.getConductor())) {
            throw new BusinessLogicException("El Viaje no tiene conductor asignado");
        }
        viaje = persistence.create(viaje);
        LOGGER.log(Level.INFO, "Termina proceso de creación del viaje");
        return viaje;
    }

    /**
     * Obtiene la lista de los registros de Viaje.
     *
     * @return Colección de objetos de ViajeEntity.
     */
    public List<ViajeEntity> getViajes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los viajes");
        return persistence.findAll();
    }

    /**
     * Busca un viaje por id
     *
     * @param viajeId El id del viaje a buscar
     * @return El Viaje encontrado, null si no lo encuentra.
     */
    public ViajeEntity getViaje(Long viajeId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el viaje con id = {0}", viajeId);
        return persistence.find(viajeId);
    }
    
    /**
     * Busca un viaje por id de un conductor tambien pasado por id
     *
     * @param viajeId El id del viaje a buscar
     * @param conductorId El id del conductor dueño del viaje
     * @return El Viaje encontrado, null si no lo encuentra.
     */
    public ViajeEntity getViaje(Long conductorId, Long viajeId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el viaje con id = {0} del conductor con id = {1}", new Object[]{viajeId, conductorId});
        return persistence.find(conductorId, viajeId);
    }

    /**
     * Obtiene la lista de los registros de Viaje con conductor dado por parametor.
     * @param conductorId id del condcutor del que se desean los viajes
     * @return Colección de objetos de ViajeEntity.
     */
    public List<ViajeEntity> getViajes(Long conductorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los viajes del conductor con id={0}", conductorId);
        return persistence.findAll(conductorId);
    }

    /**
     * Actualizar un viaje por ID
     *
     * @param viaje La entidad del libro con los cambios deseados con id dentro de el
     * @return La entidad del viaje luego de actualizarla
     * @throws BusinessLogicException Origen o destino no validos
     * Numero de cupos incorrecto
     * Fecha de salida invalida
     * Costo viaje invalido
     * Estado del viaje no es correcto
     * Vehiculo asignado no valido
     * Viaje sin conductor asignado
     */
    public ViajeEntity updateViaje(ViajeEntity viaje) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el viaje con id = {0}", viaje.getId());
        if (!validateDestino(viaje.getDestino()) || !validateOrigen(viaje.getOrigen()))// || viaje.getDestino().equals(viaje.getOrigen()))
        {
            throw new BusinessLogicException("El destino u origen son incorrectos");
        } else if (!validateCupos(viaje.getCupos())) {
            throw new BusinessLogicException("El numero de cupos incorrecto");
        } else if (!validateFechaDeSalida(viaje.getFechaDeSalida()) || !validateFechaDeLlegada(viaje.getFechaDeLlegada()))// || viaje.getFechaDeSalida().compareTo(viaje.getFechaDeLlegada())<0 )
        {
            throw new BusinessLogicException("La fecha de salida o llegada no es correcta");
        } else if (!validateCostoViaje(viaje.getCostoViaje())) {
            throw new BusinessLogicException("La costo del viaje no es correcto");
        } else if (!validateEstadoViaje(viaje.getEstadoViaje())) {
            throw new BusinessLogicException("El estado del viaje no es correcto");
        } else if (!validateVehiculo(viaje.getVehiculo())) {
            throw new BusinessLogicException("El vehiculo asignado a este viaje no existe");
        } else if (!validateConductor(viaje.getConductor())) {
            throw new BusinessLogicException("El Viaje no tiene conductor asignado");
        }
        return persistence.update(viaje);
    }

    /**
     * Elimina una instancia de Viaje de la base de datos.
     *
     * @param viajeId Identificador de la instancia a eliminar.
     */
    public void deleteViaje(Long viajeId) {
        persistence.delete(viajeId);
    }

        /**
     * Verifica que el destino no sea invalido.
     *
     * @param destino a verificar
     * @return true si el destino es valido.
     */
    private boolean validateDestino(String destino) {
        return !(destino == null || destino.isEmpty());
    }

    /**
     * Verifica que la fecha de salida no sea invalido.
     *
     * @param fechaDeSalida a verificar
     * @return true si la fechaDeSalida es valida.
     */
    private boolean validateFechaDeSalida(Date fechaDeSalida) {
        return (fechaDeSalida != null);// && fechaDeSalida.compareTo(new Date())>=0);
    }

    /**
     * Verifica que el fecha de llegada no sea invalido.
     *
     * @param fechaDeLlegada a verificar
     * @return true si la fecha de llegada es valido.
     */
    private boolean validateFechaDeLlegada(Date fechaDeLlegada) {
        return (fechaDeLlegada != null); // && fechaDeLlegada.compareTo(new Date())>=0);
    }

    /**
     * Verifica que cupos no sea invalido.
     *
     * @param cupos a verificar
     * @return true si los cupos es valido.
     */
    private boolean validateCupos(Integer cupos) {
        return (cupos != null && cupos >= 0);
    }

    /**
     * Verifica que el costo del viaje no sea invalido.
     *
     * @param costoViaje a verificar
     * @return true si el costo del viaje es valido.
     */
    private boolean validateCostoViaje(Float costoViaje) {
        return (costoViaje != null && costoViaje > 0);
    }

    /**
     * Verifica que el vehiculo no sea invalido.
     *
     * @param vehiculo a verificar
     * @return true si el vehiculo es valido.
     */
    private boolean validateVehiculo(VehiculoEntity vehiculo) {
        return (vehiculo != null && persistenceVehiculo.find(vehiculo.getId()) != null);
    }

    /**
     * Verifica que el estado del viaje no sea invalido.
     *
     * @param estaoViaje a verificar
     * @return true si el estado del viaje es valido.
     */
    private boolean validateEstadoViaje(ViajeEntity.ESTADO_DE_VIAJE estadoViaje) {
        return estadoViaje != null;
    }

    /**
     * Verifica que el origen no sea invalido.
     *
     * @param orgien a verificar
     * @return true si el origen es valido.
     */
    private boolean validateOrigen(String origen) {
        return origen != null && !origen.isEmpty();
    }

    /**
     * Verifica que el conductor no sea invalido.
     *
     * @param conductor a verificar
     * @return true si el conductor es valido.
     */
    public boolean validateConductor(ConductorEntity conductor) {
        return conductor != null && persistenceConductor.find(conductor.getId()) != null;
    }

}
