/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas Fajardo Ramirez
 */
@Stateless
public class ConductorPersistence {
    

    private static final Logger LOGGER = Logger.getLogger(ConductorPersistence.class.getName());
    
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    
    public ConductorEntity create(ConductorEntity conductor) {
        em.persist(conductor);
        return conductor;
    }
    
    public ConductorEntity find(Long conductorId){
        LOGGER.log(Level.INFO, "Consultando el conductor con id={0}", conductorId);
        return em.find(ConductorEntity.class, conductorId);
    }
    
    public List<ConductorEntity> findAll(){
        TypedQuery<ConductorEntity> query = em.createQuery("select u from ConductorEntity u", ConductorEntity.class);
        return query.getResultList();
    }
    
}
