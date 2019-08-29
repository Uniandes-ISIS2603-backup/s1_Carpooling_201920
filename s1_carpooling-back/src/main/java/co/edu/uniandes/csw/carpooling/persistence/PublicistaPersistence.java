/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
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
public class PublicistaPersistence {
   
    @PersistenceContext(unitName = "carpoolingPU")
    EntityManager em;
    
    public PublicistaEntity create(PublicistaEntity publicista){
        em.persist(publicista);
        return publicista;
    }
    
    public PublicistaEntity find(Long publicistaId){
        return em.find(PublicistaEntity.class,publicistaId);
    }
    
    public List<PublicistaEntity> findAll(){
        TypedQuery<PublicistaEntity> query = em.createQuery("select u from PublicistaEntity u",PublicistaEntity.class);
        return query.getResultList();
    }
    
    public PublicistaEntity update(PublicistaEntity publicistaEntity) {
//        LOGGER.log(Level.INFO, "Actualizando el publicista con id={0}", publicistaEntity.getId());
        return em.merge(publicistaEntity);
    }

    public void delete(Long publicistaId) {
 //       LOGGER.log(Level.INFO, "Borrando el publicista con id={0}", publicistaId);
        PublicistaEntity publicistaEntity = em.find(PublicistaEntity.class, publicistaId);
        em.remove(publicistaEntity);
    }
    
}
