/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan David Serrano
 */
@Stateless
public class ViajePersistence {
    
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    
    public ViajeEntity  create(ViajeEntity viaje){
        em.persist(viaje);
        return viaje;
    }
    
    public ViajeEntity find(Long viajesId)
    {
        return em.find(ViajeEntity.class, viajesId);
    }
    
    public ViajeEntity find(Long conductorId, Long viajeId){
        TypedQuery <ViajeEntity> query = em.createQuery("select u from ViajeEntity u where (u.conductor.id = :conductorId) and (u.id = :viajeId)", ViajeEntity.class);
        query.setParameter("viajeId", viajeId);
        query.setParameter("conductorId", conductorId);
        List<ViajeEntity> resultados = query.getResultList();
        ViajeEntity viaje = null;
        if(resultados != null && !resultados.isEmpty()){
            viaje = resultados.get(0);
        }
        return viaje;
    }
    
    public List <ViajeEntity> findAll(){
        TypedQuery <ViajeEntity> query = em.createQuery("select u from ViajeEntity u", ViajeEntity.class);
        return query.getResultList();
    }
    
    public List <ViajeEntity> findAll(Long conductorId){
        TypedQuery <ViajeEntity> query = em.createQuery("select u from ViajeEntity u where u.conductor.id = :conductorId", ViajeEntity.class);
        query.setParameter("conductorId", conductorId);
        return query.getResultList();
    }
    
    
    public ViajeEntity update(ViajeEntity viaje){
        return em.merge(viaje);
    }
    
    public void delete(Long viajeId){
        ViajeEntity viajeEntity = em.find(ViajeEntity.class, viajeId);
        em.remove(viajeEntity);
    }
}
