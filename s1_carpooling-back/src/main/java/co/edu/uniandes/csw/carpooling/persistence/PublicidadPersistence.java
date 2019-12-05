/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas Fajardo Ramirez
 */
@Stateless
public class PublicidadPersistence {

    /**
     * El entity manager
     */
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;

    /**
     * crea una nueva pùblicidad en la base de datos
     * @param publicidadEntity la publicidad a crear
     * @return la publicidad creada
     */
    public PublicidadEntity create(PublicidadEntity publicidadEntity) {
        em.persist(publicidadEntity);
        return publicidadEntity;
    }

    /**
     * Buscar una publicidad dado su id y el del publicista duenho de esta
     *
     * Busca si hay alguna publicidad asociada al id ingresado por parametro
     *
     * @param publicistasId El id del publicista 
     * @param publicidadesId El ID de la publicidad buscada
     * @return La primera publicidad encontrada 
     */
    public PublicidadEntity find(Long publicistasId, Long publicidadesId) {

        TypedQuery<PublicidadEntity> q = em.createQuery("select p from PublicidadEntity p where (p.publicista.id = :publicistaId) and (p.id = :publicidadesId)", PublicidadEntity.class);
        q.setParameter("publicistaId", publicistasId);
        q.setParameter("publicidadesId", publicidadesId);
        List<PublicidadEntity> results = q.getResultList();
        PublicidadEntity publicidad = null;
        if (results == null || results.isEmpty()) {
            // Esto es equivalente a que publicidad siga siendo null
        } else {
            publicidad = results.get(0);
        }

        return publicidad;
    }

    /**
     * Actualiza una publicidad de la base de datos
     * @param publicidadEntity la publicidad actualizada
     * @return la publicidad actualizada
     */
    public PublicidadEntity update(PublicidadEntity publicidadEntity) {
        return em.merge(publicidadEntity);
    }

    /**
     * borra una publicidad de la base de datos dado su id
     * @param publicidadId  la publicidad eliminada
     */
    public void delete(Long publicidadId) {
        PublicidadEntity publicidadEntity = em.find(PublicidadEntity.class, publicidadId);
        em.remove(publicidadEntity);
    }

    /**
     * Busca una publicidad dado su nombre
     * @param nombre el nombre de la publicidad a buscar
     * @return la primera publicidad cuyo nombre sea el ingresado por parametro
     */
    public PublicidadEntity findByName(String nombre) {
        TypedQuery query = em.createQuery("Select e From PublicidadEntity e where e.nombre = :nombre", PublicidadEntity.class);
        query = query.setParameter("nombre", nombre);
        List<PublicidadEntity> mismoNombre = query.getResultList();
        PublicidadEntity result = null;
        if (!(mismoNombre == null || mismoNombre.isEmpty())) {
            result = mismoNombre.get(0);
        }
        return result;
    }

}
