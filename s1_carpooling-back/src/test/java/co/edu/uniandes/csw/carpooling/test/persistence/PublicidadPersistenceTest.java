/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.persistence.PublicidadPersistence;
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
 * @author Nicolas Fajardi Ramirez
 */
@RunWith(Arquillian.class)
public class PublicidadPersistenceTest {
    
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(PublicidadEntity.class)
                .addClass(PublicidadPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Inject
    PublicidadPersistence pp;
    @PersistenceContext
    EntityManager entMan;
    
    @Test
    public void createTest(){
        
        PodamFactory factory = new PodamFactoryImpl();
        PublicidadEntity newentity = factory.manufacturePojo(PublicidadEntity.class);
        
        PublicidadEntity pe = pp.create(newentity);
        
        Assert.assertNotNull(pe);
        
        PublicidadEntity entity = entMan.find(PublicidadEntity.class, pe.getId());
        
        Assert.assertEquals(newentity.getNombre(), entity.getNombre());
    }
}
