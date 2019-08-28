/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author le.perezl
 */
@Stateless
public class NotificacionPersistence {
    
    
    @PersistenceContext(unitName = "CarpoolingPU")
    protected EntityManager em;
    
    public NotificacionEntity create(NotificacionEntity notificacion){
        //throw new java.lang.UnsupportedOperationException("Not supported yet");
        em.persist(notificacion);
        
        return notificacion;
    }
}
