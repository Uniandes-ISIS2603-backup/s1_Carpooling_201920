/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.TrayectoPersistence;
import java.util.List;
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
    
    public TrayectoEntity createTrayecto (TrayectoEntity trayecto) throws BusinessLogicException 
    {
        if(!validateNumPeajes(trayecto.getNumPeajes()))
            throw new BusinessLogicException("Numero de peajes erroneo");
        else if(!validateDuracion(trayecto.getDuracion()) )
            throw new BusinessLogicException("Duracion no valida ");
        else if(!validateCostoCombustible(trayecto.getCostoCombustible()))
            throw new BusinessLogicException("Costo de combustible erroneo");
        else if(!validateOrigen(trayecto.getOrigen()) || !validateDestino(trayecto.getDestino()))// || trayecto.getDestino().equals(trayecto.getOrigen()))
            throw new BusinessLogicException("Origen o destinos erroneos");
        trayecto = persistence.create(trayecto);
        return trayecto;
    }
    
    public List<TrayectoEntity> getTrayectos(){
        return persistence.findAll();
    }
    
    public TrayectoEntity getTrayecto(Long trayectoId){
        return persistence.find(trayectoId);
    }
    
    public TrayectoEntity updateTrayecto(Long trayectoId, TrayectoEntity trayecto) throws BusinessLogicException{
        if(!validateNumPeajes(trayecto.getNumPeajes()))
            throw new BusinessLogicException("Numero de peajes erroneo");
        else if(!validateDuracion(trayecto.getDuracion()) )
            throw new BusinessLogicException("Duracion no valida ");
        else if(!validateCostoCombustible(trayecto.getCostoCombustible()))
            throw new BusinessLogicException("Costo de combustible erroneo");
        else if(!validateOrigen(trayecto.getOrigen()) || !validateDestino(trayecto.getDestino()))// || trayecto.getDestino().equals(trayecto.getOrigen()))
            throw new BusinessLogicException("Origen o destinos erroneos");
        return persistence.update(trayecto);
        
    }
    
    public void deleteTrayecto(Long trayectoId){
        persistence.delete(trayectoId);
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
