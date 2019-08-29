/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import co.edu.uniandes.csw.carpooling.persistence.ViajeRecurrentePersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan David Alarcón
 */
@RunWith(Arquillian.class)
public class ViajeRecurrentePersistanceTest {
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeRecurrenteEntity.class.getPackage())
                .addPackage(ViajeRecurrentePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
    
    
    @Inject
    private ViajeRecurrentePersistence  cp;

     @PersistenceContext(unitName= "carpoolingPU")
     protected EntityManager em;
    @Test
    public void  createTest()
    {
        
        PodamFactory factory = new PodamFactoryImpl();
       
        
        ViajeRecurrenteEntity viajeRecurrente = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        ViajeRecurrenteEntity result = cp.create(viajeRecurrente);
        Assert.assertNotNull(result);
        
        ViajeRecurrenteEntity entity = em.find(ViajeRecurrenteEntity.class, result.getId());
        Assert.assertEquals(viajeRecurrente.getFrecuencia(), entity.getFrecuencia());
    }
    
}
