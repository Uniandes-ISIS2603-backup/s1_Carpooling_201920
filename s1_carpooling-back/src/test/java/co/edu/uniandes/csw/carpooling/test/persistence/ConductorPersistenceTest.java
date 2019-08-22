/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class ConductorPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class).addClass(ConductorEntity.class).addClass(ConductorPersistence.class).addAsManifestResource("META-INF/persistence.xml","persistence.xml").addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Inject
    ConductorPersistence cp;
    @PersistenceContext
    EntityManager entMan;
    
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ConductorEntity conductor = factory.manufacturePojo(ConductorEntity.class);
        ConductorEntity result = cp.create(conductor);
        Assert.assertNotNull(result);
        
        ConductorEntity entity = entMan.find(ConductorEntity.class, result.getId());
        
        Assert.assertEquals(conductor.getNombre(), entity.getNombre());
        
    }
}
