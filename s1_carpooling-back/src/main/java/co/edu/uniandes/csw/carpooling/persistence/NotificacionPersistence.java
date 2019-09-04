/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;

/**
 *
 * @author le.perezl
 */
@Stateless
public class NotificacionPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(NotificacionPersistence.class.getName());
    
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    
    public NotificacionEntity create(NotificacionEntity notificacion){
        em.persist(notificacion);
        
        return notificacion;
    }
    /**
     * Busca si hay alguna notificacion con el id que se envía de argumento
     *
     * @param notificacionId: id correspondiente a la notificacion buscada.
     * @return una notificacion.
     */
    public NotificacionEntity find(Long notificacionId) {
        return em.find(NotificacionEntity.class, notificacionId);
    }
    /**
     * Devuelve todas las notificaciones de la base de datos.
     *
     * @return una lista con todas las notificaciones que encuentre en la base de
     * datos, "select u from NotificacionEntity u" es como un "select * from
     * NotificacionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<NotificacionEntity> findAll() {
        TypedQuery<NotificacionEntity> query = em.createQuery("select u from NotificacionEntity u", NotificacionEntity.class);
        return query.getResultList();
    }
    
     /**
     * Actualiza una notificacion.
     *
     * @param notificacionEntity: la notifiacion que viene con los nuevos cambios.
     * Por ejemplo la descripcion pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una notificacion con los cambios aplicados.
     */
    public NotificacionEntity update(NotificacionEntity notificacionEntity) {
        LOGGER.log(Level.INFO, "Actualizando notificacion con id = {0}", notificacionEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la notificacion con id = {0}", notificacionEntity.getId());
        return em.merge(notificacionEntity);
    }
    /**
     *
     * Borra una notificacion de la base de datos recibiendo como argumento el id
     * de la notificacion
     *
     * @param notificacionId: id correspondiente a la notificacion a borrar.
     */
    public void delete(Long notificacionId) {
        LOGGER.log(Level.INFO, "Borrando notificacion con id = {0}", notificacionId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        NotificacionEntity entity = em.find(NotificacionEntity.class, notificacionId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la notificacion con id = {0}", notificacionId);
    }
}
