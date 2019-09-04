/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
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
public class ViajeRecurrentePersistence {
    
    @PersistenceContext(unitName= "carpoolingPU")
    protected EntityManager em;
    
    
    
    public ViajeRecurrenteEntity create(ViajeRecurrenteEntity viajeRecurrente)
    {
       em.persist(viajeRecurrente);
       return viajeRecurrente;
    }
    
         public ViajeRecurrenteEntity find(Long viajeroId) {
 
        return em.find(ViajeRecurrenteEntity.class, viajeroId);
    }
     
     public ViajeRecurrenteEntity update(ViajeRecurrenteEntity viajeRecurrenteEntity)
     {
         return em.merge(viajeRecurrenteEntity);
     }
     
         public void delete(Long viajeRecurrenteId) {
        
        ViajeRecurrenteEntity viajeRecurrenteEntity = em.find(ViajeRecurrenteEntity.class, viajeRecurrenteId);
        em.remove(viajeRecurrenteEntity);
    }
        public List<ViajeRecurrenteEntity> findAll() {
        TypedQuery<ViajeRecurrenteEntity> query = em.createQuery("select u from ViajeRecurrenteEntity u", ViajeRecurrenteEntity.class);
        return query.getResultList();
    }
}
