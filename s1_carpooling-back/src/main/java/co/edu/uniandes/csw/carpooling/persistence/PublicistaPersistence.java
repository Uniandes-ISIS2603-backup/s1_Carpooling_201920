/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
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
public class PublicistaPersistence {

    private static final Logger LOGGER = Logger.getLogger(PublicistaPersistence.class.getName());

    @PersistenceContext(unitName = "carpoolingPU")
    EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param publicistaEntity objeto publicista que se creará en la base de
     * datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public PublicistaEntity create(PublicistaEntity publicistaEntity) {
        LOGGER.log(Level.INFO, "Creando un publicista nuevo");
        em.persist(publicistaEntity);
        LOGGER.log(Level.INFO, "Viajero creado");
        return publicistaEntity;
    }

    /**
     * Busca si hay algun publicista con el id que se envía de argumento
     *
     * @param publicistaId: id correspondiente al publicista buscado.
     * @return un publicista.
     */
    public PublicistaEntity find(Long publicistaId) {
        LOGGER.log(Level.INFO, "Consultando el publicista con id={0}", publicistaId);
        return em.find(PublicistaEntity.class, publicistaId);
    }

    /**
     * Devuelve todos los publicistas de la base de datos.
     *
     * @return una lista con todos los publicistas que encuentre en la base de
     * datos, "select u from PublicistaEntity u" es como un "select * from
     * PublicistaEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<PublicistaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los publicistas");
        TypedQuery<PublicistaEntity> query = em.createQuery("select u from PublicistaEntity u", PublicistaEntity.class);
        return query.getResultList();
    }

    /**
     * Actualiza un publicista.
     *
     * @param publicistaEntity: el publicista que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un publicista con los cambios aplicados.
     */
    public PublicistaEntity update(PublicistaEntity publicistaEntity) {
        LOGGER.log(Level.INFO, "Actualizando el publicista con id={0}", publicistaEntity.getId());
        return em.merge(publicistaEntity);
    }

    /**
     *
     * Borra un publicista de la base de datos recibiendo como argumento el id
     * del publicista
     *
     * @param publicistaId: id correspondiente al publicista a borrar.
     */
    public void delete(Long publicistaId) {
        LOGGER.log(Level.INFO, "Borrando el publicista con id={0}", publicistaId);
        PublicistaEntity publicistaEntity = em.find(PublicistaEntity.class, publicistaId);
        em.remove(publicistaEntity);
    }

    public PublicistaEntity findByCorreo(String correo) {
        TypedQuery<PublicistaEntity> query = em.createQuery("Select e From PublicistaEntity e where e.correo = :correo", PublicistaEntity.class);
        query = query.setParameter("correo", correo);
        List<PublicistaEntity> mismoCorreo = query.getResultList();
        PublicistaEntity result = null;
        if (!(mismoCorreo == null || mismoCorreo.isEmpty())) {
            result = mismoCorreo.get(0);
        }
        return result;
    }
    
        public PublicistaEntity findByCedula(String cedula) {
        TypedQuery<PublicistaEntity> query = em.createQuery("Select e From PublicistaEntity e where e.cedula = :cedula", PublicistaEntity.class);
        query = query.setParameter("cedula", cedula);
        List<PublicistaEntity> mismaCedula = query.getResultList();
        PublicistaEntity result = null;
        if (!(mismaCedula == null || mismaCedula.isEmpty())) {
            result = mismaCedula.get(0);
        }
        return result;
    }

    public PublicistaEntity findByRut(String rut) {
        TypedQuery<PublicistaEntity> query = em.createQuery("Select e From PublicistaEntity e where e.rut = :rut", PublicistaEntity.class);
        query = query.setParameter("rut", rut);
        List<PublicistaEntity> mismoRut = query.getResultList();
        PublicistaEntity result = null;
        if (!(mismoRut == null || mismoRut.isEmpty())) {
            result = mismoRut.get(0);
        }
        return result;
    }

    public PublicistaEntity findByNit(String nit) {
        TypedQuery<PublicistaEntity> query = em.createQuery("Select e From PublicistaEntity e where e.nit = :nit", PublicistaEntity.class);
        query = query.setParameter("nit", nit);
        List<PublicistaEntity> mismoNit = query.getResultList();
        PublicistaEntity result = null;
        if (!(mismoNit == null || mismoNit.isEmpty())) {
            result = mismoNit.get(0);
        }
        return result;
    }
}
