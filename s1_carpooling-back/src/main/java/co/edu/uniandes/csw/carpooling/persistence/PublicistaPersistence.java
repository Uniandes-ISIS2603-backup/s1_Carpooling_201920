/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Santiago Ballesteros
 */
@Stateless
public class PublicistaPersistence {
   
    @PersistenceContext(unitName = "carpoolingPU")
    EntityManager em;
    
    public PublicistaEntity create(PublicistaEntity publicista){
        em.persist(publicista);
        return publicista;
    }
}
