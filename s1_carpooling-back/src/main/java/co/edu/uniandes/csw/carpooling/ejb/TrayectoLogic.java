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
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan David Serrano
 */
@Stateless
public class TrayectoLogic {
    
    @Inject
    private TrayectoPersistence persistence;
    
    @Inject
    private ViajePersistence viajePersistence;
    
    private static final Logger LOGGER = Logger.getLogger(TrayectoLogic.class.getName());
    
    public TrayectoEntity createTrayecto (Long viajesId, TrayectoEntity trayecto) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear trayecto");
        if(!validateNumPeajes(trayecto.getNumPeajes()))
            throw new BusinessLogicException("Numero de peajes erroneo");
        else if(!validateDuracion(trayecto.getDuracion()) )
            throw new BusinessLogicException("Duracion no valida ");
        else if(!validateCostoCombustible(trayecto.getCostoCombustible()))
            throw new BusinessLogicException("Costo de combustible erroneo");
        else if(!validateOrigen(trayecto.getOrigen()) || !validateDestino(trayecto.getDestino()))// || trayecto.getDestino().equals(trayecto.getOrigen()))
            throw new BusinessLogicException("Origen o destinos erroneos");
        ViajeEntity viajeEntity = viajePersistence.find(viajesId);
        trayecto.setViaje(viajeEntity);
        trayecto = persistence.create(trayecto);
        LOGGER.log(Level.INFO, "Termina proceso de creación del trayecto");
        return trayecto;
    }
    
    public List<TrayectoEntity> getTrayectos(Long viajesId){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los trayectos asociados al viaje con id = {0}", viajesId);
        ViajeEntity viajeEntity = viajePersistence.find(viajesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los trayectos asociados al viaje con id = {0}", viajesId);
        return viajeEntity.getTrayectos();
    }
    
    public TrayectoEntity getTrayecto(Long trayectoId, Long viajeId){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el trayecto con id = {0}", trayectoId);
        return persistence.find(trayectoId, viajeId);
    }
    
    public TrayectoEntity updateTrayecto(TrayectoEntity trayecto, Long viajesId) throws BusinessLogicException{
        if(!validateNumPeajes(trayecto.getNumPeajes()))
            throw new BusinessLogicException("Numero de peajes erroneo");
        else if(!validateDuracion(trayecto.getDuracion()) )
            throw new BusinessLogicException("Duracion no valida ");
        else if(!validateCostoCombustible(trayecto.getCostoCombustible()))
            throw new BusinessLogicException("Costo de combustible erroneo");
        else if(!validateOrigen(trayecto.getOrigen()) || !validateDestino(trayecto.getDestino()))// || trayecto.getDestino().equals(trayecto.getOrigen()))
            throw new BusinessLogicException("Origen o destinos erroneos");
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el trayecto con id = {0} del viaje con id = " + viajesId, trayecto.getId());
        ViajeEntity viajeEntity = viajePersistence.find(viajesId);
        trayecto.setViaje(viajeEntity);
        TrayectoEntity nuevo = persistence.update(trayecto);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el trayecto con id = {0} del viaje con id = " + viajesId, trayecto.getId());
        return nuevo;
    }
    
    public void deleteTrayecto(Long trayectoId, Long viajesId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el trayecto con id = {0} del viaje con id = " + viajesId, trayectoId);
        TrayectoEntity trayecto = getTrayecto(trayectoId, viajesId);
        if(trayecto == null)
            throw new BusinessLogicException("El trayecto con id = "+trayectoId+"no esta asociado con el viaje con id ="+viajesId);
        persistence.delete(trayectoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el trayecto con id = {0} del viaje con id = " + viajesId, trayectoId);
    }
    
    
    
    private boolean validateNumPeajes(Integer numPeajes){
        return numPeajes != null && numPeajes >= 0;
    }
    
    private boolean validateDuracion(Integer duracion){
        return duracion != null && duracion >= 1;
    }
    
    private boolean validateCostoCombustible(Double costoCombustible){
        return costoCombustible != null && costoCombustible >= 0;
    }
    
    private boolean validateOrigen(String origen){
        return origen != null && !origen.isEmpty();
    }
    
    private boolean validateDestino(String destino){
        return destino != null && !destino.isEmpty();
    }
    
    
}
