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

    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;

    public PublicidadEntity create(PublicidadEntity publicidadEntity) {
        em.persist(publicidadEntity);
        return publicidadEntity;
    }

    /**
     * Buscar una reseña
     *
     * Busca si hay alguna reseña asociada a un libro y con un ID específico
     *
     * @param publicistasId El ID del libro con respecto al cual se busca
     * @param publicidadesId El ID de la reseña buscada
     * @return La reseña encontrada o null. Nota: Si existe una o más reseñas
     * devuelve siempre la primera que encuentra
     */
    public PublicidadEntity find(Long publicistasId, Long publicidadesId) {

        TypedQuery<PublicidadEntity> q = em.createQuery("select p from PublicidadEntity p where (p.publicista.id = :publicistaId) and (p.id = :publicidadesId)", PublicidadEntity.class);
        q.setParameter("publicistaId", publicistasId);
        q.setParameter("publicidadesId", publicidadesId);
        List<PublicidadEntity> results = q.getResultList();
        PublicidadEntity publicidad = null;
        if (results == null || results.isEmpty()) {
            // Esto es equivalente a que publicidad siga siendo null
        } else if (results.size() >= 1) {
            publicidad = results.get(0);
        }

        return publicidad;
    }

    public PublicidadEntity update(PublicidadEntity publicidadEntity) {
        return em.merge(publicidadEntity);
    }

    public void delete(Long publicidadId) {
        PublicidadEntity publicidadEntity = em.find(PublicidadEntity.class, publicidadId);
        em.remove(publicidadEntity);
    }

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
