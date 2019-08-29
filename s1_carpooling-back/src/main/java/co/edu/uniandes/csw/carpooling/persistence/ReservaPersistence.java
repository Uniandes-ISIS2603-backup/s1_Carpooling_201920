/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author le.perezl
 */


@Stateless
public class ReservaPersistence {

    private static final Logger LOGGER = Logger.getLogger(NotificacionPersistence.class.getName());
    
    @PersistenceContext (unitName= "carpoolingPU")
    protected EntityManager em;

    public ReservaEntity create (ReservaEntity reserva){

        em.persist(reserva);
        return reserva;
    }

        /**
     * Busca si hay alguna reserva con el id que se envía de argumento
     *
     * @param reservaId: id correspondiente a la notificacion buscada.
     * @return una notificacion.
     */
    public ReservaEntity find(Long reservaId) {
        return em.find(ReservaEntity.class, reservaId);
    }
    
    /**
     * Devuelve todas las reservas de la base de datos.
     *
     * @return una lista con todas las notificaciones que encuentre en la base de
     * datos, "select u from NotificacionEntity u" es como un "select * from
     * NotificacionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ReservaEntity> findAll() {
        TypedQuery<ReservaEntity> query = em.createQuery("select u from ReservaEntity u", ReservaEntity.class);
        return query.getResultList();
    }
    
     /**
     * Actualiza una reserva.
     *
     * @param reservaEntity: la notifiacion que viene con los nuevos cambios.
     * Por ejemplo la descripcion pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una reserva con los cambios aplicados.
     */
    public ReservaEntity update(ReservaEntity reservaEntity) {
        LOGGER.log(Level.INFO, "Actualizando reserva con id = {0}", reservaEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la reserva con id = {0}", reservaEntity.getId());
        return em.merge(reservaEntity);
    }
    /**
     *
     * Borra una reserva de la base de datos recibiendo como argumento el id
     * de la reserva
     *
     * @param reservaId: id correspondiente a la reserva a borrar.
     */
    public void delete(Long reservaId) {
        LOGGER.log(Level.INFO, "Borrando reserva con id = {0}", reservaId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        ReservaEntity entity = em.find(ReservaEntity.class, reservaId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la reserva con id = {0}", reservaId);
    }
   
}
