/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.UsuarioEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajePersistence;
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
public class CalificacionPersistanceTest {

    
    @Inject
    private CalificacionPersistence  cp;

     @PersistenceContext
     protected EntityManager em;
     
     @Inject
    UserTransaction utx;
  
    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
    private List<ViajeroEntity> dataViajero = new ArrayList<ViajeroEntity>();
    private List<ConductorEntity> dataConductor = new ArrayList<ConductorEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
    


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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from ViajeroEntity").executeUpdate();
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ViajeroEntity entity = factory.manufacturePojo(ViajeroEntity.class);
            em.persist(entity);
            dataViajero.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);
            em.persist(entity);
            dataConductor.add(entity);
        }
        
        for (int i = 0; i < 3; i++) {
            ViajeroEntity entity = factory.manufacturePojo(ViajeroEntity.class);
            em.persist(entity);
            dataViajero.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            if(i==0)
            {
                entity.setConductor(dataConductor.get(0));
                entity.setViajero(null);
            }
            if(i==1)
            {
                entity.setViajero(dataViajero.get(0));
                entity.setConductor(null);
            }
            em.persist(entity);
            data.add(entity);
        }
    }

     
    @Test
    public void  createTest()
    {
        
        PodamFactory factory = new PodamFactoryImpl();
          
        CalificacionEntity calificacion = factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity result = cp.create(calificacion);
        Assert.assertNotNull(result);
        
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        Assert.assertEquals(calificacion.getPuntuacion(), entity.getPuntuacion());
        Assert.assertEquals(calificacion.getComentarios(), entity.getComentarios());
    }
    
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity ent : list) {
            boolean found = false;
            for (CalificacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getCalificacionesByViajeroTest() {
        List<CalificacionEntity> list = cp.findAllByViajero(data.get(0).getViajero().getId());
        for (CalificacionEntity ent : list) {
            boolean found = false;
            for (CalificacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getCalificacionesByConductorTest() {
        List<CalificacionEntity> list = cp.findAllByConductor(data.get(1).getViajero().getId());
        for (CalificacionEntity ent : list) {
            boolean found = false;
            for (CalificacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    @Test
    public void getCalificacionByViajeroTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity newEntity = cp.findByViajero(dataViajero.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(entity.getPuntuacion(), newEntity.getPuntuacion());
        Assert.assertEquals(entity.getComentarios(), newEntity.getComentarios());
    }
    
    @Test
    public void getCalificacionByConductorTest() {
        CalificacionEntity entity = data.get(1);
        CalificacionEntity newEntity = cp.findByConductor(dataConductor.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(entity.getPuntuacion(), newEntity.getPuntuacion());
        Assert.assertEquals(entity.getComentarios(), newEntity.getComentarios());
    }
    
    @Test
    public void getCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
     @Test
    public void updateCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
     @Test
    public void deleteCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        cp.delete(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    
}
