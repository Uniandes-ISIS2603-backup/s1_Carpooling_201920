/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
         public VehiculoEntity find(Long viajeroId) {
 
        return em.find(VehiculoEntity.class, viajeroId);
    }
     
     public VehiculoEntity update(VehiculoEntity vehiculoEntity)
     {
         return em.merge(vehiculoEntity);
     }
     
         public void delete(Long vehiculoId) {
        
        VehiculoEntity viajeroEntity = em.find(VehiculoEntity.class, vehiculoId);
        em.remove(viajeroEntity);
    }
     public List<VehiculoEntity> findAll() {
        TypedQuery<VehiculoEntity> query = em.createQuery("select u from VehiculoEntity u", VehiculoEntity.class);
        return query.getResultList();
    }
}
