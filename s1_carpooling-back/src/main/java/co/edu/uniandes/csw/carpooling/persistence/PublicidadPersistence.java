/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas Fajardo Ramirez
 */
@Stateless
public class PublicidadPersistence {
    
    
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    
    public PublicidadEntity create(PublicidadEntity publicidadEntity){
        em.persist(publicidadEntity);
        return publicidadEntity;
    }
    
    public PublicidadEntity find(Long publicidadId){
        return em.find((PublicidadEntity.class), publicidadId);
    }
    
    public List<PublicidadEntity> findALl(){
        TypedQuery<PublicidadEntity> query = em.createQuery("select u from PublicidadEntity u", PublicidadEntity.class);
        return query.getResultList();
    }
    
}
