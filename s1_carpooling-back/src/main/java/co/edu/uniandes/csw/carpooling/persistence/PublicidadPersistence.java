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
    
    public List<PublicidadEntity> findAll(){
        TypedQuery<PublicidadEntity> query = em.createQuery("select u from PublicidadEntity u", PublicidadEntity.class);
        return query.getResultList();
    }
    
    public PublicidadEntity update(PublicidadEntity publicidadEntity){
        return em.merge(publicidadEntity);
    }
    
    public void delete(Long publicidadId){
        PublicidadEntity publicidadEntity = em.find(PublicidadEntity.class, publicidadId);
        em.remove(publicidadEntity);
    }
    
    
    public PublicidadEntity findByName(String nombre){
        TypedQuery query = em.createQuery("Select e From PublicidadEntity e where e.nombre = :nombre",PublicidadEntity.class);
        query = query.setParameter("nombre",nombre);
        List<PublicidadEntity> mismoNombre = query.getResultList();
        PublicidadEntity result;
        if(mismoNombre == null){
            result = null;
        }
        else if(mismoNombre.isEmpty()){
            result = null;
        }
        else{
            result = mismoNombre.get(0);
        }
        return result;
    }
    
}
