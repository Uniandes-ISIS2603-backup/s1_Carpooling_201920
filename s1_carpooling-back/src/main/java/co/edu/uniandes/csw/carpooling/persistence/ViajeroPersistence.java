/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(ViajeroPersistence.class.getName());

    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param viajeroEntity objeto viajero que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ViajeroEntity create(ViajeroEntity viajeroEntity) {

        // Hace persistir en la base de datos a la entidad viajero
        LOGGER.log(Level.INFO, "Creando un viajero nuevo");
        em.persist(viajeroEntity);
        LOGGER.log(Level.INFO, "Viajero creado");
        return viajeroEntity;
    }

    /**
     * Busca si hay algun viaje con el id que se envía de argumento
     *
     * @param viajeroId: id correspondiente al viajero buscado.
     * @return un viajero.
     */
    public ViajeroEntity find(Long viajeroId) {
        LOGGER.log(Level.INFO, "Consultando el viajero con id={0}", viajeroId);
        return em.find(ViajeroEntity.class, viajeroId);
    }

    /**
     * Devuelve todos los viajeros de la base de datos.
     *
     * @return una lista con todos los viajeros que encuentre en la base de
     * datos, "select u from ViajeroEntity u" es como un "select * from
     * ViajeroEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ViajeroEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los viajeros");
        TypedQuery<ViajeroEntity> query = em.createQuery("select u from ViajeroEntity u", ViajeroEntity.class);
        return query.getResultList();
    }

    /**
     * Actualiza un viajero.
     *
     * @param viajeroEntity: el viajero que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un viajero con los cambios aplicados.
     */
    public ViajeroEntity update(ViajeroEntity viajeroEntity) {
        LOGGER.log(Level.INFO, "Actualizando el viajero con id={0}", viajeroEntity.getId());
        return em.merge(viajeroEntity);
    }

    /**
     *
     * Borra un viajero de la base de datos recibiendo como argumento el id del
     * viajero
     *
     * @param viajeroId: id correspondiente al viajero a borrar.
     */
    public void delete(Long viajeroId) {
        LOGGER.log(Level.INFO, "Borrando el viajero con id={0}", viajeroId);
        ViajeroEntity viajeroEntity = em.find(ViajeroEntity.class, viajeroId);
        em.remove(viajeroEntity);
    }

    public ViajeroEntity findByCorreo(String correo) {
        TypedQuery<ViajeroEntity> query = em.createQuery("Select e From ViajeroEntity e where e.correo = :correo", ViajeroEntity.class);
        query = query.setParameter("correo", correo);
        List<ViajeroEntity> mismoCorreo = query.getResultList();
        ViajeroEntity result = null;
        if (!(mismoCorreo == null || mismoCorreo.isEmpty())) {
            result = mismoCorreo.get(0);
        }
        return result;
    }
}
