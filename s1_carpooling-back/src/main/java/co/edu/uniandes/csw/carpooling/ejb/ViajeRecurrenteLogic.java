/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ViajeRecurrentePersistence;
import java.time.LocalDate;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
        

/**
 *
 * @author Juan David Alarcon
 */

@Stateless
public class ViajeRecurrenteLogic {
    @Inject
    private ViajeRecurrentePersistence persistence;
    
    public ViajeRecurrenteEntity createViajeRecurrente(ViajeRecurrenteEntity viajeRecurrente ) throws BusinessLogicException
    {
  
        if(viajeRecurrente.getFechaInicio().getTime() < System.currentTimeMillis()) 
            throw new BusinessLogicException("La fecha de inicio no puede ser menor a la actual");
        
        if(viajeRecurrente.getFechaFin().getTime() < System.currentTimeMillis()) 
            throw new BusinessLogicException("La fecha final no puede ser menor a la actual");
        
        if(viajeRecurrente.getFechaFin().getTime() < viajeRecurrente.getFechaInicio().getTime()) 
            throw new BusinessLogicException("La fecha final no puede ser menor a la fecha inicial");
        
     viajeRecurrente = persistence.create(viajeRecurrente);
     return viajeRecurrente;
    }
    
    
}
