/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.persistence.ReservaPersistence;
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
//import static org.glassfish.pfl.basic.tools.argparser.ElementParser.factory;
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
 * @author le.perezl
 */
@RunWith(Arquillian.class)

public class ReservaPersistenceTest {
    @PersistenceContext (unitName= "carpoolingPU")
    protected EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ReservaEntity.class)
                .addClass(ReservaPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Inject
    ReservaPersistence rp;
    
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        ReservaEntity reserva = factory.manufacturePojo(ReservaEntity.class);
        ReservaEntity result = rp.create(reserva);
    
    Assert.assertNotNull(result);
    
    ReservaEntity entity = em.find(ReservaEntity.class, result.getId());
    
    Assert.assertEquals(reserva.getConfirmacion(), entity.getConfirmacion());
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
}
