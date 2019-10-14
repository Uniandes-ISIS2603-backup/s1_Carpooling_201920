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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan David Serrano
 */
@Stateless
public class ViajeLogic {
    
    @Inject
    private ViajePersistence persistence;
    
    @Inject
    private VehiculoPersistence persistenceVehiculo;
    
    @Inject
    private ConductorPersistence persistenceConductor;
    
    
    public ViajeEntity createViaje(ViajeEntity viaje) throws BusinessLogicException{
        if ( !validateDestino(viaje.getDestino()) || !validateOrigen(viaje.getOrigen()))// || viaje.getDestino().equals(viaje.getOrigen()))
            throw new BusinessLogicException("El destino u origen son incorrectos");
        else if (!validateCupos(viaje.getCupos()))
            throw new BusinessLogicException("El numero de cupos incorrecto");
        else if (!validateFechaDeSalida(viaje.getFechaDeSalida()) || !validateFechaDeLlegada(viaje.getFechaDeLlegada()))// || viaje.getFechaDeSalida().compareTo(viaje.getFechaDeLlegada())<0 )
            throw new BusinessLogicException("La fecha de salida o llegada no es correcta");
        else if (!validateCostoViaje(viaje.getCostoViaje()))
            throw new BusinessLogicException("La costo del viaje no es correcto");
        else if (!validateEstadoViaje(viaje.getEstadoViaje()))
            throw new BusinessLogicException("El estado del viaje no es correcto");
        else if(!validateVehiculo(viaje.getVehiculo()))
            throw new BusinessLogicException("El vehiculo asignado a este viaje no existe");
        else if(!validateConductor(viaje.getConductor()))
            throw new BusinessLogicException("El Viaje no tiene conductor asignado");
        viaje = persistence. create(viaje);
        return viaje;
    }
    
    public List<ViajeEntity> getViajes(){
        return persistence.findAll();
    }
    
    public ViajeEntity getViaje(Long viajeId){
        return persistence.find(viajeId);
    }
    
    public ViajeEntity getViaje(Long conductorId ,Long viajeId){
        return persistence.find(conductorId, viajeId);
    }
    
    
    
    public List<ViajeEntity> getViajes(Long conductorId){
        return persistence.findAll(conductorId);
    }
    
    public ViajeEntity updateViaje(ViajeEntity viaje) throws BusinessLogicException{
        if ( !validateDestino(viaje.getDestino()) || !validateOrigen(viaje.getOrigen()))// || viaje.getDestino().equals(viaje.getOrigen()))
            throw new BusinessLogicException("El destino u origen son incorrectos");
        else if (!validateCupos(viaje.getCupos()))
            throw new BusinessLogicException("El numero de cupos incorrecto");
        else if (!validateFechaDeSalida(viaje.getFechaDeSalida()) || !validateFechaDeLlegada(viaje.getFechaDeLlegada()))// || viaje.getFechaDeSalida().compareTo(viaje.getFechaDeLlegada())<0 )
            throw new BusinessLogicException("La fecha de salida o llegada no es correcta");
        else if (!validateCostoViaje(viaje.getCostoViaje()))
            throw new BusinessLogicException("La costo del viaje no es correcto");
        else if (!validateEstadoViaje(viaje.getEstadoViaje()))
            throw new BusinessLogicException("El estado del viaje no es correcto");
        else if(!validateVehiculo(viaje.getVehiculo()))
            throw new BusinessLogicException("El vehiculo asignado a este viaje no existe");
        else if(!validateConductor(viaje.getConductor()))
            throw new BusinessLogicException("El Viaje no tiene conductor asignado");
         return persistence.update(viaje);
    }
    
    public void deleteViaje(Long viajeId){
        persistence.delete(viajeId);
    }
    
    
    private boolean validateDestino(String destino){
        return !(destino == null || destino.isEmpty());
    }
    
    private boolean validateFechaDeSalida(Date fechaDeSalida){
        return (fechaDeSalida != null);// && fechaDeSalida.compareTo(new Date())>=0);
    }
    
    private boolean validateFechaDeLlegada(Date fechaDeLlegada){
        return (fechaDeLlegada != null); // && fechaDeLlegada.compareTo(new Date())>=0);
    }
    
    private boolean validateCupos(Integer cupos){
        return (cupos != null && cupos>=0);
    }
    
    private boolean validateCostoViaje(Float costoViaje){
        return (costoViaje != null && costoViaje > 0);
    }
    
    private boolean validateVehiculo(VehiculoEntity vehiculo){
        return (vehiculo != null && persistenceVehiculo.find(vehiculo.getId()) != null);
    }
            
    private boolean validateEstadoViaje(ViajeEntity.ESTADO_DE_VIAJE estadoViaje)
    {
        return estadoViaje != null;
    }
    
    
    private boolean validateOrigen(String origen){
        return origen != null && !origen.isEmpty();
    }
    
    public boolean validateConductor(ConductorEntity conductor){
        return conductor != null && persistenceConductor.find(conductor.getId()) != null;
            
    }

    
}
 