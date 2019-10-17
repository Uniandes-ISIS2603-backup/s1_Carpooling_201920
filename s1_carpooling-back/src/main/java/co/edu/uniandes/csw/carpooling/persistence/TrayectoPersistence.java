/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan David Serrano
 */
@Stateless
public class TrayectoPersistence {

    private static final Logger LOGGER = Logger.getLogger(TrayectoPersistence.class.getName());

    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;

    public TrayectoEntity create(TrayectoEntity trayecto) {
        em.persist(trayecto);
        return trayecto;
    }

    public TrayectoEntity find(Long trayectosId) {
        return em.find(TrayectoEntity.class, trayectosId);
    }

    public TrayectoEntity find(Long trayectosId, Long viajesId) {
        LOGGER.log(Level.INFO, "Consultando el trayecto con id = {0} del viaje con id = " + viajesId, trayectosId);
        TypedQuery<TrayectoEntity> q = em.createQuery("select p from TrayectoEntity p where (p.viaje.id = :viajesid) and (p.id = :trayectosid)", TrayectoEntity.class);
        q.setParameter("viajesid", viajesId);
        q.setParameter("trayectosid", trayectosId);
        List<TrayectoEntity> resultados = q.getResultList();
        TrayectoEntity trayecto = null;
        if (resultados != null && !resultados.isEmpty()) {
            trayecto = resultados.get(0);
        }
        return trayecto;
    }

    public List<TrayectoEntity> findAll() {
        TypedQuery<TrayectoEntity> query = em.createQuery("select u from TrayectoEntity u", TrayectoEntity.class);
        return query.getResultList();
    }

    public TrayectoEntity update(TrayectoEntity trayecto) {
        return em.merge(trayecto);
    }

    public void delete(Long trayectoId) {
        TrayectoEntity trayectoEntity = em.find(TrayectoEntity.class, trayectoId);
        em.remove(trayectoEntity);
    }

}
