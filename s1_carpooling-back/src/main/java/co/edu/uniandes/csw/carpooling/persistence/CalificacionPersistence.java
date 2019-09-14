/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan David Alarcón
 */
//Stateless le informa a JPA (EJB)que se trata cada peticion como transacción independiente (sin relacion con solicitudes anteriores)
@Stateless
public class CalificacionPersistence {
    
    //La anotacion permite asociar el em con la unidad de persistencia 
    @PersistenceContext(unitName= "carpoolingPU")
    //El em es que permite las operaciones sobre la base de datos
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
