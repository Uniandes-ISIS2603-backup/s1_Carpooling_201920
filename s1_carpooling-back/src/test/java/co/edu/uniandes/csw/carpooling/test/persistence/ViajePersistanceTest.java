/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.persistence.ViajePersistance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan David Serrano
 */
@RunWith(Arquillian.class)
public class ViajePersistanceTest {
    
    @Inject
    ViajePersistance vp;
    
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em2;
    
    @Deployment
    public static JavaArchive createDeployment() {
            return ShrinkWrap.create(JavaArchive.class)
                    .addClass(ViajeEntity.class)
                    .addClass(ViajePersistance.class) 
                    .addAsManifestResource("META-INF/persistence.xml", "persistence.xml") 
                    .addAsManifestResource("META-INF/beans.xml", "beans.xml" ); 
    }
    
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ViajeEntity viaje = factory.manufacturePojo(ViajeEntity.class);
        ViajeEntity result = vp.create(viaje);
        Assert.assertNotNull(result);
        ViajeEntity entity = em2.find(ViajeEntity.class, result.getId());
        Assert.assertEquals(viaje.getDestino(), entity.getDestino());

    } 
}
