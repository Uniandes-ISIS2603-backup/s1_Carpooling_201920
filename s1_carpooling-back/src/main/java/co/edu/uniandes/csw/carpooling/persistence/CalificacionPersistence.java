/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan David Alarcón
 */

@Stateless
public class CalificacionPersistence {
    
    @PersistenceContext(unitName= "carpoolingPU")
    protected EntityManager em;
    
    
    
    public CalificacionEntity create(CalificacionEntity calificacion)
    {
       em.persist(calificacion);
       return calificacion;
    }
    
     public CalificacionEntity find(Long viajeroId) {
 
        return em.find(CalificacionEntity.class, viajeroId);
    }
     
     public CalificacionEntity update(CalificacionEntity calificacionEntity)
     {
         return em.merge(calificacionEntity);
     }
     
         public void delete(Long calificacionId) {
        
        CalificacionEntity viajeroEntity = em.find(CalificacionEntity.class, calificacionId);
        em.remove(viajeroEntity);
    }
         
    public List<CalificacionEntity> findAll() {
        TypedQuery<CalificacionEntity> query = em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
        return query.getResultList();
    }
    
}
