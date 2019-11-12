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
 * Clase que maneja la persistencia para Trayecto. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 * @author Juan David Serrano
 */
@Stateless
public class TrayectoPersistence {

    private static final Logger LOGGER = Logger.getLogger(TrayectoPersistence.class.getName());

    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;

    /**
     * Crea un Trayecto en la base de datos
     *
     * @param trayecto objeto trayecto que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public TrayectoEntity create(TrayectoEntity trayecto) {
        LOGGER.log(Level.INFO, "Creando un trayecto nuevo");
        em.persist(trayecto);
        LOGGER.log(Level.INFO, "Trayecto creado");
        return trayecto;
    }

    /**
     * Busca si hay algun trayecto con el id que se envía de argumento
     *
     * @param trayectosId: id correspondiente al trayecto buscado.
     * @return un trayecto.
     */
    public TrayectoEntity find(Long trayectosId) {
        LOGGER.log(Level.INFO, "Consultando el trayecto con id={0}", trayectosId);
        return em.find(TrayectoEntity.class, trayectosId);
    }

     /**
     * Busca si hay algun trayecto con un id que se envía de argumento de un
     * viaje con un id pasado por argumento
     *
     * @param trayectosId: Id del trayecto que se está buscando
     * @param viajesId: id del viaje dueño del trayecto que se busca
     * @return null si no existe ningun trayecto con el id del argumento o del 
     * viaje del otro argumento. Si existe alguno devuelve el primero.
     */
    public TrayectoEntity find(Long trayectosId, Long viajesId) {
        LOGGER.log(Level.INFO, "Consultando trayecto por id: {0} y del viaje con id: {1}", new Object[]{trayectosId, viajesId});
        TypedQuery<TrayectoEntity> q = em.createQuery("select p from TrayectoEntity p where (p.viaje.id = :viajesid) and (p.id = :trayectosid)", TrayectoEntity.class);
        q.setParameter("viajesid", viajesId);
        q.setParameter("trayectosid", trayectosId);
        List<TrayectoEntity> resultados = q.getResultList();
        TrayectoEntity trayecto = null;
        if (resultados != null && !resultados.isEmpty()) {
            trayecto = resultados.get(0);
        }
        LOGGER.log(Level.INFO, "Retornando trayecto por consulta a partir de viaje ");
        return trayecto;
    }

    /**
     * Devuelve todas las trayectos de la base de datos.
     *
     * @return una lista con todas los trayectos que encuentre en la base de
     * datos, "select u from TrayectoEntity u" es como un "select * from
     * TrayectoEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<TrayectoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los trayectos");
        TypedQuery<TrayectoEntity> query = em.createQuery("select u from TrayectoEntity u", TrayectoEntity.class);
        return query.getResultList();
    }

    public TrayectoEntity update(TrayectoEntity trayecto) {
        return em.merge(trayecto);
    }

    /**
     *
     * Borra un trayecto de la base de datos recibiendo como argumento el id del
     * trayecto
     *
     * @param trayectoId: id correspondiente al viaje a borrar.
     */
    public void delete(Long trayectoId) {
        LOGGER.log(Level.INFO, "Borrando el trayecto con id={0}", trayectoId);
        TrayectoEntity trayectoEntity = em.find(TrayectoEntity.class, trayectoId);
        em.remove(trayectoEntity);
    }

}
