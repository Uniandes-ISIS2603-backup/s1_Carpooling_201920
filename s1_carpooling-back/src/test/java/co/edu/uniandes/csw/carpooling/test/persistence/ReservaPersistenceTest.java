/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.persistence.ReservaPersistence;
import java.util.ArrayList;
import java.util.List;
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
@RunWith(Arquillian.class)

public class ReservaPersistenceTest {
    @PersistenceContext (unitName= "carpoolingPU")
    protected EntityManager em;
    
    private List<ReservaEntity> data = new ArrayList<ReservaEntity>();
         
    @Inject
    UserTransaction utx;
    
    @Inject
    ReservaPersistence rp;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReservaEntity.class.getPackage())
                .addPackage(ReservaPersistence.class.getPackage())
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
        em.createQuery("delete from ReservaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ReservaEntity entity = factory.manufacturePojo(ReservaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
  
    
    
    
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        ReservaEntity reserva = factory.manufacturePojo(ReservaEntity.class);
        ReservaEntity result = rp.create(reserva);
    
    Assert.assertNotNull(result);
    
    ReservaEntity entity = em.find(ReservaEntity.class, result.getId());
    
    Assert.assertEquals(reserva.getConfirmacion(), entity.getConfirmacion());
    Assert.assertEquals(reserva.getNumeroDeReserva(), entity.getNumeroDeReserva());
    Assert.assertEquals(reserva.getFecha(), entity.getFecha());
    Assert.assertEquals(reserva.getEstado(), entity.getEstado());
    }
    
//    public void createNotificacionTest(){
//         // Falta crear notif
//          PodamFactory factory = new PodamFactoryImpl();
//          NotificacionEntity notificacion = factory.manufacturePojo(NotificacionEntity.class);
//          NotificacionEntity result= np.create(notificacion);
//          Assert.assertNotNull(result);
//          
//          NotificacionEntity entity= em.find(NotificacionEntity.class, result.getId());
//          
//          Assert.assertEquals(notificacion.getMensaje(),entity.getMensaje());
//      }
     /**
     * Prueba para consultar la lista de Reservas.
     */
    @Test
    public void findAllTest() {
        List<ReservaEntity> list = rp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ReservaEntity ent : list) {
            boolean found = false;
            for (ReservaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
      /**
     * Prueba para consultar una Reservas.
     */
     @Test
    public void findTest() {
        ReservaEntity entity = data.get(0);
        ReservaEntity newEntity =rp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumeroDeReserva(), newEntity.getNumeroDeReserva());
    }

    /**
     * Prueba para eliminar una Reservas.
     */
    @Test
    public void deleteTest() {
        ReservaEntity entity = data.get(0);
        rp.delete(entity.getId());
        ReservaEntity deleted = em.find(ReservaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    /**
     * Prueba para actualizar una Reservas.
     */
    @Test
    public void updateTest() {
        ReservaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);

        newEntity.setId(entity.getId());

        rp.update(newEntity);

        ReservaEntity resp = em.find(ReservaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNumeroDeReserva(), resp.getNumeroDeReserva());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getEstado(), resp.getEstado());
         Assert.assertEquals(newEntity.getConfirmacion(), resp.getConfirmacion());
    }
    
}
