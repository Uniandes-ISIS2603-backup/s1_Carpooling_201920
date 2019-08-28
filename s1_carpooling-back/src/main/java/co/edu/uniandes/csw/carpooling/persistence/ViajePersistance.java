/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan David Serrano
 */
@Stateless
public class ViajePersistance {
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    public ViajeEntity  create(ViajeEntity viaje){
        em.persist(viaje);
        return viaje;
        //throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }
}
