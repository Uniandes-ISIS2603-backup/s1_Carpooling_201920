/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Viaje. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 * @author Juan David Serrano
 */
@Stateless
public class ViajePersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ViajePersistence.class.getName());

    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    
    
    /**
     * Crea un Viaje en la base de datos
     *
     * @param viaje objeto viaje que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ViajeEntity create(ViajeEntity viaje) {
        LOGGER.log(Level.INFO, "Creando un viaje nuevo");
        em.persist(viaje);
        LOGGER.log(Level.INFO, "viaje creado");
        return viaje;
    }

    /**
     * Busca si hay alguna viaje con el id que se envía de argumento
     *
     * @param viajesId: id correspondiente al viaje buscado.
     * @return un viaje.
     */
    public ViajeEntity find(Long viajesId) {
        LOGGER.log(Level.INFO, "Consultando el viaje con id={0}", viajesId);
        return em.find(ViajeEntity.class, viajesId);
    }

     /**
     * Busca si hay algun viaje con un id que se envía de argumento de un
     * conductor con un id pasado por argumento
     *
     * @param conductorId: id de del conductor que posee el viaje
     * @param viajeId: id del viaje que se está buscando
     * @return null si no existe ningun viaje con el id del argumento o del 
     * autor del otro argumento. Si existe alguno devuelve el primero.
     */
    public ViajeEntity find(Long conductorId, Long viajeId) {
        LOGGER.log(Level.INFO, "Consultando viajes por id: {0} y del conductor con id: {1}", new Object[]{viajeId, conductorId});
        TypedQuery<ViajeEntity> query = em.createQuery("select u from ViajeEntity u where (u.conductor.id = :conductorId) and (u.id = :viajeId)", ViajeEntity.class);
        query.setParameter("viajeId", viajeId);
        query.setParameter("conductorId", conductorId);
        List<ViajeEntity> resultados = query.getResultList();
        ViajeEntity viaje = null;
        if (resultados != null && !resultados.isEmpty()) {
            viaje = resultados.get(0);
        }
        LOGGER.log(Level.INFO, "Retornando viaje pod consulta a partir de conductor ");
        return viaje;
    }
    
    /**
     * Devuelve todas las viajes de la base de datos.
     *
     * @return una lista con todas los viajes que encuentre en la base de
     * datos, "select u from ViajeEntity u" es como un "select * from
     * ViajeEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ViajeEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los viajes");
        TypedQuery<ViajeEntity> query = em.createQuery("select u from ViajeEntity u", ViajeEntity.class);
        return query.getResultList();
    }
    
     /**
     * Devuelve todas las viajes de un conductor de la base de datos.
     *
     * @param conductorId Id del conductor que se desean los viajes
     * @return una lista con todas los viajes que encuentre en la base de
     * datos, "select u from ViajeEntity u" es como un "select * from
     * ViajeEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ViajeEntity> findAll(Long conductorId) {
        TypedQuery<ViajeEntity> query = em.createQuery("select u from ViajeEntity u where u.conductor.id = :conductorId", ViajeEntity.class);
        query.setParameter("conductorId", conductorId);
        return query.getResultList();
    }

    /**
     * Actualiza un viaje.
     *
     * @param viaje: el viaje que viene con los nuevos cambios. Por
     * ejemplo la fecha pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una viaje con los cambios aplicados.
     */
    public ViajeEntity update(ViajeEntity viaje) {
        LOGGER.log(Level.INFO, "Actualizando el viaje con id={0}", viaje.getId());
        return em.merge(viaje);
    }

    /**
     *
     * Borra un viaje de la base de datos recibiendo como argumento el id del
     * libro
     *
     * @param viajeId: id correspondiente al viaje a borrar.
     */
    public void delete(Long viajeId) {
        LOGGER.log(Level.INFO, "Borrando el viaje con id={0}", viajeId);
        ViajeEntity viajeEntity = em.find(ViajeEntity.class, viajeId);
        em.remove(viajeEntity);
    }
}
