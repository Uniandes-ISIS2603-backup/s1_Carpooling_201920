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
    @PersistenceContext(unitName = "carpoolingPU")
    //El em es que permite las operaciones sobre la base de datos
    protected EntityManager em;

    public CalificacionEntity create(CalificacionEntity calificacion) {
        em.persist(calificacion);
        return calificacion;
    }

    public CalificacionEntity find(Long calificacionId) {

        return em.find(CalificacionEntity.class, calificacionId);
    }

    public List<CalificacionEntity> findAllByViajero(Long viajeroId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.viajero.id = :viajeroid)", CalificacionEntity.class);
        q.setParameter("viajeroid", viajeroId);
        List<CalificacionEntity> results = q.getResultList();
        return results;
    }

    public List<CalificacionEntity> findAllByConductor(Long conductorId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.conductor.id = :conductorid)", CalificacionEntity.class);
        q.setParameter("conductorid", conductorId);
        List<CalificacionEntity> results = q.getResultList();
        return results;
    }

    public CalificacionEntity findByConductor(Long conductorId, Long calificacionId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.conductor.id = :conductorid) and (p.id = :calificacionId)", CalificacionEntity.class);

        q.setParameter("conductorid", conductorId);
  
        q.setParameter("calificacionId", calificacionId);

        
        List<CalificacionEntity> results = q.getResultList();
        CalificacionEntity calificacion = null;
        if (results == null) {
            calificacion = null;
        } else if (results.isEmpty()) {
            calificacion = null;
        } else if (results.size() >= 1) {
            calificacion = results.get(0);
        }
    
        return calificacion;
    }

    public CalificacionEntity findByViajero(Long viajeroId, Long calificacionId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.viajero.id = :viajeroid) and (p.id = :calificacionId)", CalificacionEntity.class);
        q.setParameter("viajeroid", viajeroId);
        q.setParameter("calificacionId", calificacionId);
        List<CalificacionEntity> results = q.getResultList();
        CalificacionEntity calificacion = null;
        if (results == null) {
            calificacion = null;
        } else if (results.isEmpty()) {
            calificacion = null;
        } else if (results.size() >= 1) {
            calificacion = results.get(0);
        }
    
        return calificacion;
    }

    public CalificacionEntity update(CalificacionEntity calificacionEntity) {
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