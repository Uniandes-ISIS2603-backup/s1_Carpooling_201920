/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import co.edu.uniandes.csw.carpooling.persistence.ViajeRecurrentePersistence;
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
     
     
     
        
    @Inject
    UserTransaction utx;
  
    private List<ViajeRecurrenteEntity> data = new ArrayList<ViajeRecurrenteEntity>();
    private List<ConductorEntity> dataConductor = new ArrayList<ConductorEntity>();
    
    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from ViajeRecurrenteEntity").executeUpdate();
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        
        for (int i = 0; i < 3; i++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);
            em.persist(entity);
            dataConductor.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ViajeRecurrenteEntity entity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
            if(i==0)
            {
                entity.setConductor(dataConductor.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void  createTest()
    {
        
        PodamFactory factory = new PodamFactoryImpl();
       
        
        ViajeRecurrenteEntity viajeRecurrente = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        ViajeRecurrenteEntity result = cp.create(viajeRecurrente);
        Assert.assertNotNull(result);
        
        ViajeRecurrenteEntity entity = em.find(ViajeRecurrenteEntity.class, result.getId());
        Assert.assertEquals(viajeRecurrente.getFrecuencia(), entity.getFrecuencia());
        Assert.assertEquals(viajeRecurrente.getFechaFin(), entity.getFechaFin());
        Assert.assertEquals(viajeRecurrente.getFechaInicio(), entity.getFechaInicio());
    }
    
    @Test
    public void getViajeRecurrenteByConductorTest() {
        ViajeRecurrenteEntity entity = data.get(0);
        Long idCalificacion = entity.getId();
        ConductorEntity conductor = dataConductor.get(0);
        Long idConductor = conductor.getId();
        ViajeRecurrenteEntity newEntity = cp.find(idConductor, idCalificacion );
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(entity.getFrecuencia(), newEntity.getFrecuencia());
        Assert.assertEquals(entity.getFechaFin(), newEntity.getFechaFin());
        Assert.assertEquals(entity.getFechaInicio(), newEntity.getFechaInicio());
    }
    
    @Test
    public void getViajesRecurrentesTest() {
        List<ViajeRecurrenteEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ViajeRecurrenteEntity ent : list) {
            boolean found = false;
            for (ViajeRecurrenteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    @Test
    public void getViajeRecurrenteTest() {
        ViajeRecurrenteEntity entity = data.get(0);
        ViajeRecurrenteEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
     @Test
    public void updateViajeRecurrenteTest() {
        ViajeRecurrenteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        ViajeRecurrenteEntity resp = em.find(ViajeRecurrenteEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
     @Test
    public void deleteViajeRecurrenteTest() {
        ViajeRecurrenteEntity entity = data.get(0);
        cp.delete(entity.getId());
        ViajeRecurrenteEntity deleted = em.find(ViajeRecurrenteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
