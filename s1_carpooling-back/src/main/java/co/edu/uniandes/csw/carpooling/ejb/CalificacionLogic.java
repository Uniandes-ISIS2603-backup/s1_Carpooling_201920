/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;
import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.CalificacionPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
        

/**
 *
 * @author Juan David Alarcon
 */

@Stateless
public class CalificacionLogic {
    @Inject
    private CalificacionPersistence persistence;
    
    public CalificacionEntity createCalificacion(CalificacionEntity calificacion ) throws BusinessLogicException
    {
        if(calificacion.getComentarios().length()> 500)
        {
            throw new BusinessLogicException("El comentario no puede ser mayor a 500 caracteres");
        }
        
        if(calificacion.getPuntuacion() < 0 || calificacion.getPuntuacion() > 5)
        {
            throw new BusinessLogicException("La puntuacion no puede ser menor a cero o mayor a 5");
        }
        
     calificacion = persistence.create(calificacion);
     return calificacion;
    }
    
    
}
