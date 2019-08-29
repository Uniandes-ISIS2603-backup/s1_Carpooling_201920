/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David Alarcón
 */

@Stateless
public class VehiculoPersistence {
    
    @PersistenceContext(unitName= "carpoolingPU")
    protected EntityManager em;
    
    
    
    public VehiculoEntity create(VehiculoEntity vehiculo)
    {
       em.persist(vehiculo);
       return vehiculo;
    }
    
}
