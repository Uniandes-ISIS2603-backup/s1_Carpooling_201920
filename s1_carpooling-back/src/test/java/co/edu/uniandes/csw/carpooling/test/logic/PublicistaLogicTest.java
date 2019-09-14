/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.PublicistaLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicistaPersistence;
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
 * @author SantiagoBallesteros
 */
@RunWith(Arquillian.class)
public class PublicistaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PublicistaLogic publicistaLogic;
    
     @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment(){
    return ShrinkWrap.create(JavaArchive.class)
            .addPackage(PublicistaEntity.class.getPackage())
            .addPackage(PublicistaLogic.class.getPackage())
            .addPackage(PublicistaPersistence.class.getPackage())
            .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
            .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Test
    public void createPublicista() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);
        Assert.assertNotNull(result);
        
        PublicistaEntity entity = em.find(PublicistaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(result.getApellido(), entity.getApellido());
        Assert.assertEquals(result.getCedula(), entity.getCedula());
        Assert.assertEquals(result.getCorreo(), entity.getCorreo());
        Assert.assertEquals(result.getNit(), entity.getNit());
        Assert.assertEquals(result.getRut(), entity.getRut());
        Assert.assertEquals(result.getTelefono(), entity.getTelefono());
        Assert.assertEquals(result.getTipoPublicista(), entity.getTipoPublicista());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaNombreNull() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        newEntity.setNombre(null);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);
    }
}
