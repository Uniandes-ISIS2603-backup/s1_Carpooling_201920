/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    public ViajeroEntity find(Long viajeroId){
        return em.find(ViajeroEntity.class,viajeroId);
    }
    
    public List<ViajeroEntity> findAll(){
        TypedQuery<ViajeroEntity> query = em.createQuery("select u from ViajeroEntity u",ViajeroEntity.class);
        return query.getResultList();
    }
    
    
}
