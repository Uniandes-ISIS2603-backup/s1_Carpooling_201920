/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Santiago Ballesteros
 */
@Stateless
public class ViajeroPersistence {
  
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    
    public ViajeroEntity create(ViajeroEntity viajero){
        
        // Hace persistir en la base de datos a la entidad viajero
        em.persist(viajero);
        return viajero;        
    }
    
}
