/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.VehiculoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
        

/**
 *
 * @author Juan David Alarcon
 */

@Stateless
public class VehiculoLogic {
    @Inject
    private VehiculoPersistence persistence;
    
    public VehiculoEntity createVehiculo(VehiculoEntity vehiculo ) throws BusinessLogicException
    {
        
        if(vehiculo.getPlaca().length() != 7)
            throw new BusinessLogicException("La placa tiene que tener 7 caracteres (incluyendo el espacio en la mitad)");
        
        String[] placa =  vehiculo.getPlaca().split(" ");
        
        if(placa[0].matches("[0-9]+") || !placa[1].matches("[0-9]+"))
            throw new BusinessLogicException("La placa tiene que tener el formato 'XXX 123' ");        
        
        if(vehiculo.getSillas() < 1 || vehiculo.getSillas() > 10)
            throw new BusinessLogicException("El vehiculo tienen que tener entre 1 y 10 sillas");
        

     vehiculo = persistence.create(vehiculo);
     return vehiculo;
    }
    
    
}
