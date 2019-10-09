/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.VehiculoLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
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
    private List<ConductorEntity> dataConductor = new ArrayList<ConductorEntity>();
    
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
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }

    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);
            em.persist(entity);
            dataConductor.add(entity);
        }
        
        
        for (int i = 0; i < 3; i++) {
            VehiculoEntity entity = factory.manufacturePojo(VehiculoEntity.class);
            if(i==0)
            {
                entity.setConductor(dataConductor.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
        data.get(2).setPlaca("ABC 123");
        data.get(2).setSillas(5);
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
    @Test
    public void createVehiculoForConductorTest() throws BusinessLogicException {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        newEntity.setPlaca(data.get(2).getPlaca());
        newEntity.setSillas(data.get(2).getSillas());
        newEntity.setConductor(dataConductor.get(0));
        VehiculoEntity result = vehiculoLogic.createVehiculo(dataConductor.get(0).getId(), newEntity);
        Assert.assertNotNull(result);
        VehiculoEntity entity = em.find(VehiculoEntity.class, result.getId());
              
        Assert.assertEquals(entity.getAseguradora(), result.getAseguradora());
        Assert.assertEquals(entity.getModelo(), result.getModelo());
        Assert.assertEquals(entity.getPlaca(), result.getPlaca());
        Assert.assertEquals(entity.getSillas(), result.getSillas());
        Assert.assertEquals(entity.getSoat(), result.getSoat());
        Assert.assertEquals(entity.getVigenciaSoat(), result.getVigenciaSoat());
    }
    
    @Test
    public void getVehiculosByConductorTest() throws BusinessLogicException {
        List<VehiculoEntity> list = vehiculoLogic.getVehiculos(dataConductor.get(0).getId());
        for (VehiculoEntity entity : list) {
            boolean found = false;
            for (VehiculoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
         @Test
    public void getVehiculoByConductorTest() {
        VehiculoEntity entity = data.get(0);
        VehiculoEntity resultEntity = vehiculoLogic.getVehiculo(dataConductor.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
 
        
        Assert.assertEquals(entity.getAseguradora(), resultEntity.getAseguradora());
        Assert.assertEquals(entity.getModelo(), resultEntity.getModelo());
        Assert.assertEquals(entity.getPlaca(), resultEntity.getPlaca());
        Assert.assertEquals(entity.getSillas(), resultEntity.getSillas());
        Assert.assertEquals(entity.getSoat(), resultEntity.getSoat());
        Assert.assertEquals(entity.getVigenciaSoat(), resultEntity.getVigenciaSoat());
    }
    
    public void updateVehiculoByConductorTest() {
        VehiculoEntity entity = data.get(0);
        VehiculoEntity pojoEntity = factory.manufacturePojo(VehiculoEntity.class);

        pojoEntity.setId(entity.getId());

        vehiculoLogic.updateVehiculo(dataConductor.get(0).getId(), pojoEntity);

        VehiculoEntity resp = em.find(VehiculoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
      
        Assert.assertEquals(pojoEntity.getAseguradora(), resp.getAseguradora());
        Assert.assertEquals(pojoEntity.getModelo(), resp.getModelo());
        Assert.assertEquals(pojoEntity.getPlaca(), resp.getPlaca());
        Assert.assertEquals(pojoEntity.getSillas(), resp.getSillas());
        Assert.assertEquals(pojoEntity.getSoat(), resp.getSoat());
        Assert.assertEquals(pojoEntity.getVigenciaSoat(), resp.getVigenciaSoat());
    }
        @Test
    public void deleteVehiculoByConductorTest() throws BusinessLogicException {
        VehiculoEntity entity = data.get(0);
        vehiculoLogic.deleteVehiculo(dataConductor.get(0).getId(), entity.getId());
        VehiculoEntity deleted = em.find(VehiculoEntity.class, entity.getId());
        Assert.assertNull(deleted);
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
