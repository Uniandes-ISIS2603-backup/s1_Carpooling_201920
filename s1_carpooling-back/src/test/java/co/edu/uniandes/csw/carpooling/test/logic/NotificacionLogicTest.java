/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.NotificacionLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.NotificacionPersistence;
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
 * @author le.perezl
 */
@RunWith(Arquillian.class)
public class NotificacionLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private NotificacionLogic notificacionLogic;
        
    @Inject
    private UserTransaction utx;
    
    private List<NotificacionEntity> data = new ArrayList<NotificacionEntity>();
    private List<ViajeroEntity> dataViajero = new ArrayList<ViajeroEntity>();
    private List<ConductorEntity> dataConductor = new ArrayList<ConductorEntity>();
    @Deployment
    public static JavaArchive createDeployment()
    {
       
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(NotificacionEntity.class.getPackage())
                .addPackage(NotificacionLogic.class.getPackage())
                .addPackage(NotificacionPersistence.class.getPackage())
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
        em.createQuery("delete from NotificacionEntity").executeUpdate();
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
            NotificacionEntity notif = factory.manufacturePojo(NotificacionEntity.class);
            ArrayList notifs = new ArrayList();
            notifs.add(notif);
            entity.setNotificaciones(notifs);
            em.persist(entity);
            dataConductor.add(entity);
           
            
        }
        
        for (int i = 0; i < 3; i++) {
            NotificacionEntity entity = factory.manufacturePojo(NotificacionEntity.class);
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
    public void createNotificacionTest() throws BusinessLogicException{
        NotificacionEntity newEntity = factory.manufacturePojo(NotificacionEntity.class);
        NotificacionEntity result = notificacionLogic.createNotificacion(newEntity);
        Assert.assertNotNull(result);
        
        NotificacionEntity entity = em.find(NotificacionEntity.class, result.getId());
        Assert.assertEquals(entity.getMensaje(), result.getMensaje());
        Assert.assertEquals(entity.getTitulo(), result.getTitulo());
        Assert.assertEquals(entity.getFecha(), result.getFecha());
    }
    
        @Test
    public void createNotificacionForConductorTest() throws BusinessLogicException {
        NotificacionEntity newEntity = factory.manufacturePojo(NotificacionEntity.class);
        newEntity.setTitulo("test");
        newEntity.setConductor(dataConductor.get(0));
        NotificacionEntity result = notificacionLogic.createNotificacionForConductor(dataConductor.get(0).getId(), newEntity);
        Assert.assertNotNull(result);
        NotificacionEntity entity = em.find(NotificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(entity.getMensaje(), result.getMensaje());
        Assert.assertEquals(entity.getTitulo(), result.getTitulo());
        Assert.assertEquals(entity.getFecha(), result.getFecha());
    }
    
    @Test
    public void createNotificacionForViajeroTest() throws BusinessLogicException {
        NotificacionEntity newEntity = factory.manufacturePojo(NotificacionEntity.class);
        newEntity.setTitulo("test");
        newEntity.setViajero(dataViajero.get(0));
        NotificacionEntity result = notificacionLogic.createNotificacionForViajero(dataViajero.get(0).getId(), newEntity);
        Assert.assertNotNull(result);
        NotificacionEntity entity = em.find(NotificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(entity.getMensaje(), result.getMensaje());
        Assert.assertEquals(entity.getTitulo(), result.getTitulo());
        Assert.assertEquals(entity.getFecha(), result.getFecha());
    }
         @Test
    public void getNotificacionesByConductorTest() throws BusinessLogicException {
        List<NotificacionEntity> list = notificacionLogic.getNotificacionesByConductor(dataConductor.get(0).getId());
        for (NotificacionEntity entity : list) {
            boolean found = false;
            for (NotificacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getNotificacionesByViajeroTest() throws BusinessLogicException {
        List<NotificacionEntity> list = notificacionLogic.getNotificacionesByViajero(dataViajero.get(0).getId());
        for (NotificacionEntity entity : list) {
            boolean found = false;
            for (NotificacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
//       @Test
//    public void getNotificacionByConductorTest() {
//        NotificacionEntity entity = data.get(0);
//        NotificacionEntity resultEntity = notificacionLogic.getNotificacionByConductor(dataConductor.get(0).getId(), entity.getId());
//        Assert.assertNotNull(resultEntity);
//        Assert.assertEquals(entity.getId(), resultEntity.getId());
//        Assert.assertEquals(entity.getTitulo(), resultEntity.getTitulo());
//        Assert.assertEquals(entity.getMensaje(), resultEntity.getMensaje());
//        Assert.assertEquals(entity.getFecha(), resultEntity.getFecha());
//    }
    
    @Test
    public void getNotificacionByViajeroTest() {
        NotificacionEntity entity = data.get(1);
        NotificacionEntity resultEntity = notificacionLogic.getNotificacionByViajero(dataViajero.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getTitulo(), resultEntity.getTitulo());
        Assert.assertEquals(entity.getMensaje(), resultEntity.getMensaje());
        Assert.assertEquals(entity.getFecha(), resultEntity.getFecha());
    }
     @Test
    public void updateNotificacionByConductorTest() {
        NotificacionEntity entity = data.get(0);
        NotificacionEntity pojoEntity = factory.manufacturePojo(NotificacionEntity.class);

        pojoEntity.setId(entity.getId());

        notificacionLogic.updateNotificacionByConductor(dataConductor.get(0).getId(), pojoEntity);

        NotificacionEntity resp = em.find(NotificacionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getTitulo(), resp.getTitulo());
        Assert.assertEquals(pojoEntity.getMensaje(), resp.getMensaje());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
    }
    
    @Test
    public void updateNotificacionByViajeroTest() {
        NotificacionEntity entity = data.get(1);
        NotificacionEntity pojoEntity = factory.manufacturePojo(NotificacionEntity.class);

        pojoEntity.setId(entity.getId());

        notificacionLogic.updateNotificacionByViajero(dataViajero.get(0).getId(), pojoEntity);

        NotificacionEntity resp = em.find(NotificacionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getTitulo(), resp.getTitulo());
        Assert.assertEquals(pojoEntity.getMensaje(), resp.getMensaje());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
}
    
}