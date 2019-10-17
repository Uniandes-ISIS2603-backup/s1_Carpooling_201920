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

    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;

    public ViajeRecurrenteEntity create(ViajeRecurrenteEntity viajeRecurrente) {
        em.persist(viajeRecurrente);
        return viajeRecurrente;
    }

    public ViajeRecurrenteEntity find(Long conductorId) {

        return em.find(ViajeRecurrenteEntity.class, conductorId);
    }

    public ViajeRecurrenteEntity update(ViajeRecurrenteEntity viajeRecurrenteEntity) {
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

    public ViajeRecurrenteEntity find(Long conductoresId, Long viajesRecurrentesId) {

        TypedQuery<ViajeRecurrenteEntity> q = em.createQuery("select p from ViajeRecurrenteEntity p where (p.conductor.id = :conductorid) and (p.id = :viajesRecurrentesId)", ViajeRecurrenteEntity.class);
        q.setParameter("conductorid", conductoresId);
        q.setParameter("viajesRecurrentesId", viajesRecurrentesId);
        List<ViajeRecurrenteEntity> results = q.getResultList();
        ViajeRecurrenteEntity viajeRecurrente = null;
        if (results == null||results.isEmpty()) {
            // Esto es equivalente a que viaje recurrente siga siendo null
        } else if (results.size() >= 1) {
            viajeRecurrente = results.get(0);
        }

        return viajeRecurrente;
    }
}
