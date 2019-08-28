/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.persistence.PublicistaPersistence;
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
 * @author Santiago Ballesteros
 */
@RunWith(Arquillian.class)
public class PublicistaPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class).
                addClass(PublicistaEntity.class).
                addClass(PublicistaPersistence.class).
                addAsManifestResource("META-INF/persistence.xml","persistence.xml").     
                addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Inject
    PublicistaPersistence pp;
    
    @PersistenceContext 
    EntityManager em;
    
    @Test
    public void createTest(){
    PodamFactory factory = new PodamFactoryImpl();
    PublicistaEntity publicista = factory.manufacturePojo(PublicistaEntity.class);
    PublicistaEntity result = pp.create(publicista);
    
    Assert.assertNotNull(result);
    
    PublicistaEntity entity = 
            em.find(PublicistaEntity.class, result.getId());
    
    Assert.assertEquals(publicista.getApellido(),entity.getApellido());
    Assert.assertEquals(publicista.getCedula(),entity.getCedula());
    Assert.assertEquals(publicista.getCorreo(),entity.getCorreo());
    Assert.assertEquals(publicista.getNit(),entity.getNit());
    Assert.assertEquals(publicista.getNombre(),entity.getNombre());
    Assert.assertEquals(publicista.getRut(),entity.getRut());
    Assert.assertEquals(publicista.getTelefono(),entity.getTelefono());
    Assert.assertEquals(publicista.getTipoPublicista(),entity.getTipoPublicista());
    }
}
