/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.VehiculoLogic;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.VehiculoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan David Alarcón
 */

@RunWith(Arquillian.class)
public class VehiculoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private VehiculoLogic vehiculoLogic;
        
    @Inject
    private UserTransaction utx;
    
    private List<VehiculoEntity> data = new ArrayList<VehiculoEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
       
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VehiculoEntity.class.getPackage())
                .addPackage(VehiculoLogic.class.getPackage())
                .addPackage(VehiculoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
                
    }
    
      /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
        private void clearData() {
        em.createQuery("delete from VehiculoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            VehiculoEntity entity = factory.manufacturePojo(VehiculoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test (expected = BusinessLogicException.class )
    public void createVehiculoTest() throws BusinessLogicException{
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity);
        Assert.assertNotNull(result);
        
        VehiculoEntity entity = em.find(VehiculoEntity.class, result.getId());
        Assert.assertEquals(entity.getAseguradora(), result.getAseguradora());
        Assert.assertEquals(entity.getModelo(), result.getModelo());
        Assert.assertEquals(entity.getPlaca(), result.getPlaca());
        Assert.assertEquals(entity.getSillas(), result.getSillas());
        Assert.assertEquals(entity.getSoat(), result.getSoat());
        Assert.assertEquals(entity.getVigenciaSoat(), result.getVigenciaSoat());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createVehiculoTestPlacaInvalida() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        
        newEntity.setSillas(4);
        newEntity.setPlaca("ABC123");
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity); 
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createVehiculoTestPlacaInvalida2() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        
        newEntity.setSillas(4);
        newEntity.setPlaca("A12 123");
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity); 
    }
    
        @Test (expected = BusinessLogicException.class)
    public void createVehiculoTestPlacaInvalida3() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        
        newEntity.setSillas(4);
        newEntity.setPlaca("ABC 1AB");
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity); 
    }
    
     @Test
    public void createVehiculoTestPlacaValida() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        
        newEntity.setSillas(4);        
        newEntity.setPlaca("ABC 123");
        
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity);  
    }
    
    
    @Test (expected = BusinessLogicException.class)
    public void createVehiculoTestSillasInvalida() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        
        
        newEntity.setPlaca("ABC 123");
        newEntity.setSillas(0);
        
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity); 
    }
    
        public void createVehiculoTestSillasInvalida2() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        
        
        newEntity.setPlaca("ABC 123");
        newEntity.setSillas(11);
        
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity); 
    }
    
     @Test
    public void createVehiculoTestSillaValida() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
               
        newEntity.setPlaca("ABC 123");
        newEntity.setSillas(4); 
        
        VehiculoEntity result = vehiculoLogic.createVehiculo(newEntity);  
    }
    
    
    
}
