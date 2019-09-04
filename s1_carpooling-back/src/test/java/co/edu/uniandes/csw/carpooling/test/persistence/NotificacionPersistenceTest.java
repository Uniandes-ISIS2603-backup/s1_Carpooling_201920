/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.persistence.NotificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
//import static org.glassfish.pfl.basic.tools.argparser.ElementParser.factory;
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
//preg si los test van con otro nombre o el del metodo
@RunWith(Arquillian.class)
public class NotificacionPersistenceTest {
    
    private NotificacionPersistence NotificacionPersistence;
    private NotificacionEntity NotificacionEntity;
    
    private List<NotificacionEntity> data = new ArrayList<NotificacionEntity>();
         
    @Inject
    UserTransaction utx;
    
    @Inject
    NotificacionPersistence np;
    
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
        .addClass(NotificacionEntity.class)
        .addClass(NotificacionPersistence.class)
        .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
        .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    //DA ERROR EL CONFIG
        /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
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
        /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from NotificacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            NotificacionEntity entity = factory.manufacturePojo(NotificacionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    

      
      @Test
      public void createTest(){
         // Falta crear notif
          PodamFactory factory = new PodamFactoryImpl();
          NotificacionEntity notificacion = factory.manufacturePojo(NotificacionEntity.class);
          NotificacionEntity result= np.create(notificacion);
          Assert.assertNotNull(result);
          
          NotificacionEntity entity= em.find(NotificacionEntity.class, result.getId());
          
          Assert.assertEquals(notificacion.getMensaje(),entity.getMensaje());
          Assert.assertEquals(notificacion.getFecha(),entity.getFecha());
      }
      /**
     * Prueba para consultar la lista de Notificaciones.
     */
    @Test
    public void findAllTest() {
        List<NotificacionEntity> list = np.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (NotificacionEntity ent : list) {
            boolean found = false;
            for (NotificacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
      /**
     * Prueba para consultar una Notificacion.
     */
     @Test
    public void findTest() {
        NotificacionEntity entity = data.get(0);
        NotificacionEntity newEntity =np.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getMensaje(), newEntity.getMensaje());
    }

    /**
     * Prueba para eliminar una Notificacion.
     */
    @Test
    public void deleteTest() {
        NotificacionEntity entity = data.get(0);
        np.delete(entity.getId());
        NotificacionEntity deleted = em.find(NotificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    /**
     * Prueba para actualizar una Notificacion.
     */
    @Test
    public void updateTest() {
        NotificacionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        NotificacionEntity newEntity = factory.manufacturePojo(NotificacionEntity.class);

        newEntity.setId(entity.getId());

        np.update(newEntity);

        NotificacionEntity resp = em.find(NotificacionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getMensaje(), resp.getMensaje());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
    }


}
